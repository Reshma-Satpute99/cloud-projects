package com.inn.network_vxlan_app.dto;

public class NetworkDeletetionRequestDTO {

	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer networkDeletionId;
	private String org_name;
	private Integer id;
	private String namespace_name;
	private String veth_inter;
	private String bridge_name;

	public NetworkDeletetionRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NetworkDeletetionRequestDTO(Integer networkDeletionId, String org_name, Integer id, String namespace_name,
			String veth_inter, String bridge_name) {
		super();
		this.networkDeletionId = networkDeletionId;
		this.org_name = org_name;
		this.id = id;
		this.namespace_name = namespace_name;
		this.veth_inter = veth_inter;
		this.bridge_name = bridge_name;
	}

	public Integer getNetworkDeletionId() {
		return networkDeletionId;
	}

	public void setNetworkDeletionId(Integer networkDeletionId) {
		this.networkDeletionId = networkDeletionId;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNamespace_name() {
		return namespace_name;
	}

	public void setNamespace_name(String namespace_name) {
		this.namespace_name = namespace_name;
	}

	public String getVeth_inter() {
		return veth_inter;
	}

	public void setVeth_inter(String veth_inter) {
		this.veth_inter = veth_inter;
	}

	public String getBridge_name() {
		return bridge_name;
	}

	public void setBridge_name(String bridge_name) {
		this.bridge_name = bridge_name;
	}

	@Override
	public String toString() {
		return "NetworkDeleteRequest [networkDeletionId=" + networkDeletionId + ", org_name=" + org_name + ", id=" + id
				+ ", namespace_name=" + namespace_name + ", veth_inter=" + veth_inter + ", bridge_name=" + bridge_name
				+ "]";
	}

}
