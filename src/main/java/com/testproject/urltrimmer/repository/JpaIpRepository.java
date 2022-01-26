package com.testproject.urltrimmer.repository;

import com.testproject.urltrimmer.model.IpAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaIpRepository extends JpaRepository<IpAddress, Integer> {

    Optional<IpAddress> getByUrlIdAndIp(Integer urlId, String address);

    List<IpAddress> getAllByUrlId(Integer urlId);
}
