package structure;

public class GoodsNode {
	String imageUrl;
	String title;
	String orgPrice;
	String discountPrice;
	String sellerRating;
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOrgPrice() {
		return orgPrice;
	}
	public void setOrgPrice(String orgPrice) {
		this.orgPrice = orgPrice;
	}
	public String getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(String discountPrice) {
		this.discountPrice = discountPrice;
	}
	public String getSellerRating() {
		return sellerRating;
	}
	public void setSellerRating(String sellerRating) {
		this.sellerRating = sellerRating;
	}
}