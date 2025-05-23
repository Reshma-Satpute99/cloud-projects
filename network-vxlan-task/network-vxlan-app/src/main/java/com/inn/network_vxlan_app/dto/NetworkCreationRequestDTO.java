package com.inn.network_vxlan_app.dto;

public class NetworkCreationRequestDTO {

	private String org_name;
	private String id;
	private String pool_range;
	private String pool_cidr;
	private String default_gateway;

	// this is is for network creation
	private String vnet_id;
	private Boolean dhcp_enable; // true/false
	private String lease_time;
	private String dhcp_start;
	private String dhcp_end;

	public NetworkCreationRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NetworkCreationRequestDTO(String org_name, String id, String pool_range, String pool_cidr,
			String default_gateway, String vnet_id, Boolean dhcp_enable, String lease_time, String dhcp_start,
			String dhcp_end) {
		super();
		this.org_name = org_name;
		this.id = id;
		this.pool_range = pool_range;
		this.pool_cidr = pool_cidr;
		this.default_gateway = default_gateway;
		this.vnet_id = vnet_id;
		this.dhcp_enable = dhcp_enable;
		this.lease_time = lease_time;
		this.dhcp_start = dhcp_start;
		this.dhcp_end = dhcp_end;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPool_range() {
		return pool_range;
	}

	public void setPool_range(String pool_range) {
		this.pool_range = pool_range;
	}

	public String getPool_cidr() {
		return pool_cidr;
	}

	public void setPool_cidr(String pool_cidr) {
		this.pool_cidr = pool_cidr;
	}

	public String getDefault_gateway() {
		return default_gateway;
	}

	public void setDefault_gateway(String default_gateway) {
		this.default_gateway = default_gateway;
	}

	public String getVnet_id() {
		return vnet_id;
	}

	public void setVnet_id(String vnet_id) {
		this.vnet_id = vnet_id;
	}

	public Boolean getDhcp_enable() {
		return dhcp_enable;
	}

	public void setDhcp_enable(Boolean dhcp_enable) {
		this.dhcp_enable = dhcp_enable;
	}

	public String getLease_time() {
		return lease_time;
	}

	public void setLease_time(String lease_time) {
		this.lease_time = lease_time;
	}

	public String getDhcp_start() {
		return dhcp_start;
	}

	public void setDhcp_start(String dhcp_start) {
		this.dhcp_start = dhcp_start;
	}

	public String getDhcp_end() {
		return dhcp_end;
	}

	public void setDhcp_end(String dhcp_end) {
		this.dhcp_end = dhcp_end;
	}

	@Override
	public String toString() {
		return "NetworkCreationRequestDTO [org_name=" + org_name + ", id=" + id + ", pool_range=" + pool_range
				+ ", pool_cidr=" + pool_cidr + ", default_gateway=" + default_gateway + ", vnet_id=" + vnet_id
				+ ", dhcp_enable=" + dhcp_enable + ", lease_time=" + lease_time + ", dhcp_start=" + dhcp_start
				+ ", dhcp_end=" + dhcp_end + "]";
	}

}
