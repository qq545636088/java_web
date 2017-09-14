package cn.jeeweb.modules.test.service.impl;

import cn.jeeweb.core.common.service.impl.CommonServiceImpl;
import cn.jeeweb.modules.test.entity.TestOrderTicket;
import cn.jeeweb.modules.test.service.ITestOrderTicketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**   
 * @Title: 机票信息
 * @Description: 机票信息
 * @author jeeweb
 * @date 2017-07-24 12:10:30
 * @version V1.0   
 *
 */
@Transactional
@Service("testOrderTicketService")
public class TestOrderTicketServiceImpl  extends CommonServiceImpl<TestOrderTicket> implements  ITestOrderTicketService {

}
