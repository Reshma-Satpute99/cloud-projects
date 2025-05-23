package com.inn.network_vxlan_app.service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inn.network_vxlan_app.dto.NetworkCreationRequestDTO;
import com.inn.network_vxlan_app.entity.Network;
import com.inn.network_vxlan_app.repository.NetworkRepository;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class NetworkService {

	@Autowired
	private NetworkRepository networkRepository;

	/**
	 * Custom OkHttpClient with increased timeouts
	 */
	private final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
			.writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build();

	/**
	 * This is an api to trigger the vxlan-new.sh bash script which is running on
	 * the javalin server.
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Network networkCreationScript(NetworkCreationRequestDTO requestDto) throws IOException {
		System.out.println("Executing script with request: " + requestDto);

		// Prepare the URL for the Javalin service
		HttpUrl url = HttpUrl.parse("http://192.168.1.240:7001/run-script").newBuilder().build();
		System.out.println("URL prepared: " + url);

		// Prepare the JSON body with the request parameters
		String jsonBody = new ObjectMapper().writeValueAsString(requestDto);
		System.out.println("JSON body: " + jsonBody);

		// Create the request body
		RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json"));

		// Build the HTTP request
		Request httpRequest = new Request.Builder().url(url).post(body).build();

		try {
			System.out.println("Before HTTP call...");
			try (Response response = client.newCall(httpRequest).execute()) {
				// Log the full response body to validate its contents
				String responseBody = response.body() != null ? response.body().string() : null;
				System.out.println("Full response body from Javalin: " + responseBody); // Log the raw response for
																						// debugging

				// Parse the JSON response from Javalin
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode responseJson = objectMapper.readTree(responseBody);

				// Log the parsed JSON to verify that org_id is present
				System.out.println("Parsed response JSON: " + responseJson.toString());

				// Save the result to the database
				Network result = new Network();
				result.setOrg_name(requestDto.getOrg_name());
				result.setPool_range(requestDto.getPool_range());
				result.setPool_cidr(requestDto.getPool_cidr());
				result.setDefault_gateway(requestDto.getDefault_gateway());
				result.setDhcp_enable(requestDto.getDhcp_enable());
				result.setDhcp_start(requestDto.getDhcp_start());
				result.setDhcp_end(requestDto.getDhcp_end());
				result.setLease_time(requestDto.getLease_time());

				// Set id and vnet_id from Javalin response if present
				if (responseJson.has("id") && !responseJson.get("id").isNull()) {
					result.setId(responseJson.get("id").asText());
				}
				if (responseJson.has("vnet_id") && !responseJson.get("vnet_id").isNull()) {
					result.setVnet_id(responseJson.get("vnet_id").asText());
				}

				// Ensure org_id is set from Javalin response if present
				if (responseJson.has("org_id") && !responseJson.get("org_id").isNull()) {
					String orgId = responseJson.get("org_id").asText();
					System.out.println("Parsed org_id: " + orgId); // Log the org_id value
					result.setOrg_id(orgId);
				} else {
					System.out.println("org_id is missing in the response.");
				}

				// Capture the returned parameters (even if not required for creation)
				if (responseJson.has("namespace_name")) {
					result.setNamespace_name(responseJson.get("namespace_name").asText());
				}

				if (responseJson.has("veth_inter")) {
					result.setVeth_inter(responseJson.get("veth_inter").asText());
				}

				if (responseJson.has("bridge_name")) {
					result.setBridge_name(responseJson.get("bridge_name").asText());
				}

				// Save the network entity to the database
				Network saved = networkRepository.save(result);
				System.out.println("Saved network: " + saved);

				return saved;
			}
		} catch (Exception e) {
			System.out.println("Exception during HTTP call: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	
	
	/**
	 * This is an api to delete the existing network based on the org_id 
	 * @param org_id
	 * @return
	 * @throws IOException
	 */
	public boolean networkDeletionScript(String org_id) throws IOException {
		System.out.println("Executing delete script with request: " + org_id);

		// Fetch the network details based on org_id 
		Network network = networkRepository.findNetworkByOrgId(org_id); 

		if (network == null) {
			System.out.println("Network with org_id " + org_id + " not found.");
			return false;
		}

		// Prepare the URL for the Javalin service (the script execution endpoint)
		HttpUrl url = HttpUrl.parse("http://192.168.1.240:7000/delete-network").newBuilder().build();
		System.out.println("URL prepared: " + url);

		// Prepare the JSON body with the network details
		String jsonBody = new ObjectMapper().writeValueAsString(network);
		System.out.println("JSON body for delete request: " + jsonBody);

		// Create the request body
		RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json"));

		// Build the HTTP request
		Request httpRequest = new Request.Builder().url(url).delete(body).build();

		try {
			System.out.println("Before HTTP call...");
			try (Response response = client.newCall(httpRequest).execute()) {
				String responseBody = response.body() != null ? response.body().string() : null;
				System.out.println("Full response body from Javalin: " + responseBody);

				if (response.isSuccessful()) {
					// Optionally delete from MySQL based on org_id
					networkRepository.deleteByOrgId(org_id); // Assuming org_id is unique
					System.out.println("Network deleted successfully from Javalin and MySQL.");
					return true;
				} else {
					System.out.println("Network deletion failed. Status: " + response.code());
					return false;
				}
			}
		} catch (Exception e) {
			System.out.println("Exception during HTTP call: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

}
