package nc.ui.so.m30.billui.action.printaction;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import nc.ui.pubapp.pub.power.PowerCheckUtils;
import nc.ui.pubapp.uif2app.actions.MetaDataBasedPrintAction;
import nc.ui.pubapp.uif2app.model.BillManageModel;
import nc.vo.pubapp.pattern.exception.ExceptionUtils;
import nc.vo.pubapp.pub.power.PowerActionEnum;
import nc.vo.scmpub.res.billtype.SOBillType;
import nc.vo.so.m30.entity.SaleOrderHVO;
import nc.vo.so.m30.entity.SaleOrderVO;
import nc.vo.so.pub.enumeration.BillStatus;

public class SaleOrderPrintAction extends MetaDataBasedPrintAction {
	private static final long serialVersionUID = 993409051896966967L;

	public void doAction(ActionEvent e) throws Exception {
		SaleOrderVO bill = (SaleOrderVO) getModel().getSelectedData();
		SaleOrderVO[] printbills = new SaleOrderVO[] { bill };

		PowerCheckUtils.checkHasPermission(printbills,SOBillType.Order.getCode(),PowerActionEnum.PRINT.getActioncode(), "vbillcode");
		
		//---begin-----δ����ͨ�������۶������ܴ�ӡԤ�����--modified--by--yegz--2017-10-17----------//
		//���δ����ͨ�������۶�����
		List<String> vbillnos=new ArrayList<String>();
        Object[] objs=((BillManageModel) this.getModel()).getSelectedOperaDatas();
        if(null!=objs&&objs.length>0){
        	for (int i=0;i<objs.length;i++) {
        		SaleOrderVO aggVO=(SaleOrderVO) objs[i];
        		SaleOrderHVO hvo=aggVO.getParentVO();
        		//���۶���״̬����������ͨ�����߲����ڹرգ����ܴ�ӡ��Ԥ�������
        		if(BillStatus.I_AUDIT != hvo.getFstatusflag() && BillStatus.I_CLOSED !=hvo.getFstatusflag()){
        			vbillnos.add(hvo.getVbillcode());
        		}
			}
    		if (vbillnos.size()>0) {
    			String errmsg="";
    			for (String billno : vbillnos) {
    				String msg="���۶�����" +billno+ "��δ����ͨ����ر�״̬���ܴ�ӡ��\n";
    				errmsg=errmsg+msg;
    			}
    			ExceptionUtils.wrappBusinessException(errmsg);
    		}
        }
       //---end-----δ����ͨ�������۶������ܴ�ӡԤ�����--modified--by--yegz--2017-10-17----------//

		super.doAction(e);
	}
	
	
}