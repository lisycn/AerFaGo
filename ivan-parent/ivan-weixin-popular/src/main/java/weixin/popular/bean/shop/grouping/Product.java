package weixin.popular.bean.shop.grouping;
/**
 * 分组商品
 * @author 周立波
 *
 */
public class Product {
	//商品ID
	private String product_id;
	//修改操作	(0-删除, 1-增加)
	private String mod_action;
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getMod_action() {
		return mod_action;
	}
	public void setMod_action(String mod_action) {
		this.mod_action = mod_action;
	}
	
}
