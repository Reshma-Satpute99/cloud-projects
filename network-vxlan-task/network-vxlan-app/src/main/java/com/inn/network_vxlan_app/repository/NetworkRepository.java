package com.inn.network_vxlan_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inn.network_vxlan_app.entity.Network;

import jakarta.transaction.Transactional;

@Repository
public interface NetworkRepository extends JpaRepository<Network, Integer> {

	@Modifying
    @Transactional
    @Query("DELETE FROM Network n WHERE n.org_id = :orgId")
    int deleteByOrgId(String orgId);

	 @Query("SELECT n FROM Network n WHERE n.org_id = :org_id")
	    Network findNetworkByOrgId(@Param("org_id") String org_id);
	

}
