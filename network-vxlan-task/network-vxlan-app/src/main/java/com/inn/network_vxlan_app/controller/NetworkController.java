package com.inn.network_vxlan_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inn.network_vxlan_app.dto.NetworkCreationRequestDTO;
import com.inn.network_vxlan_app.entity.Network;
import com.inn.network_vxlan_app.service.NetworkService;

@RestController
@RequestMapping("/network")
public class NetworkController {

	@Autowired
	private NetworkService networkService;

	public NetworkController(NetworkService networkService) {
		this.networkService = networkService;
	}

	
	/**
	 * This is an api to call network creation api from service class 
	 * @param requestDto
	 * @return
	 */
	@PostMapping("/create")
    public ResponseEntity<Network> runScript(@RequestBody NetworkCreationRequestDTO requestDto) {
        // Log the incoming request
        System.out.println("Received request: " + requestDto);

        try {
            // Call the service method to execute the script and return the result
            Network savedNetwork = networkService.networkCreationScript(requestDto);
            return ResponseEntity.ok(savedNetwork); // Return the saved network as a response
        } catch (Exception e) {
            // Handle any exceptions that occur during execution
            e.printStackTrace();
            return ResponseEntity.status(500).body(null); // Return a 500 error if something goes wrong
        }
    }
	
	/**
	 * This is an api to call  delete-network api from service class 
	 * @param org_id
	 * @return
	 */
	 @DeleteMapping("/delete/{org_id}")
	    public ResponseEntity<String> deleteNetwork(@PathVariable String org_id) {
	        try {
	            // Call the service to run the script and delete the network
	            networkService.networkDeletionScript(org_id);
	            return ResponseEntity.ok("Network deleted successfully.");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(500).body("Error deleting network: " + e.getMessage());
	        }
	    }
	

}
