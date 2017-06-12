


/*
 * Copyright (c) 2013-2014, starteasy Inc. All Rights Reserved.
 * 
 * Project Name: codegen
 * $Id:  2017-06-10 09:21:12 $ 
 */
package com.power.test;






import com.google.common.collect.Maps;
import com.power.PowerUserStarter;
import com.power.core.domain.Sorter;
import com.power.core.domain.SqlOrderEnum;
import com.power.core.utils.SorterBuilder;
import com.power.dao.IPlatformInfoDAO;
import com.power.domain.PlatformInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@JdbcTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ExampleNonTransactionalTests {
    @Autowired
    IPlatformInfoDAO countPlatformInfoBy;

    @Test
    public void testDaoBySelector() {
        Map<String, Object> selector = Maps.newHashMap();
        selector.put("id", "id");

        List<Sorter> sorterList = SorterBuilder.sorterList("password", SqlOrderEnum.DESC);
        sorterList.add(new Sorter("login", SqlOrderEnum.DESC));

        List<PlatformInfo> adminUserList = countPlatformInfoBy.queryList(selector, Maps.newHashMap(), sorterList);

        assertNull(adminUserList.get(0).getAesKey());
        assertNotNull(adminUserList.get(0).getAgencyId());
//
//        selector.put("login", "name");
//        adminUserList = adminUserDAO.queryList(selector, Maps.newHashMap(), sorterList);
//
//        assertNotNull(adminUserList.get(0).getName());
//        assertNotNull(adminUserList.get(0).getPassword());
        adminUserList.size();
    }

}
