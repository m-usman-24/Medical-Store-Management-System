package pharmacy;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
public class Product implements Dependable, Serializable {

		private String productId;
		private String manufacturer;
		private String nameOfProduct;
		private String category;
		private String unitOfMeasure;
		private String manufactureDate; // "2001-03-24"
		private String expiryDate;
		private long batchNumber;
		private double potency;
		private long remainingStock;
		private double wholeSalePrice;
		private double retailPrice;
		
		public Product(String productId, String nameOfProduct, int potency, String unitOfMeasure, long batchNumber,
		               String category, String manufacturer, String manufactureDate, String expiryDate,
		               double wholeSalePrice, double retailPrice, long remainingStock) {
				
				setProductId(productId);
				setNameOfProduct(nameOfProduct);
				setPotency(potency);
				setUnitOfMeasure(unitOfMeasure);
				setBatchNumber(batchNumber);
				setCategory(category);
				setManufacturer(manufacturer);
				setManufactureDate(manufactureDate);
				setExpiryDate(expiryDate);
				setWholeSalePrice(wholeSalePrice);
				setRetailPrice(retailPrice);
				setRemainingStock(remainingStock);
		}
		
		@Override
		public String toString() {
				return "Name Of Product: " + nameOfProduct + "\n" +
								"Category of Product: " + category + "\n" +
								"Potency of Product: " + potency + unitOfMeasure + "\n" +
								"Manufacturer of Product: " + manufacturer + "\n" +
								"Expiry Date of Product: " + expiryDate + "\n" +
								"Manufacture Date of Product: " + manufactureDate + "\n" +
								"Batch Number of Product: " + batchNumber + "\n" +
								"Whole Sale Price of Product: " + wholeSalePrice + "\n" +
								"Retail Price of Product: " + retailPrice + "\n" +
								"Remaining Stock of Product: " + remainingStock + "\n";
		}
		
		public String getSpecificProductDetails() {
				
				return "Product ID: " + productId + "\tProduct Name: " + nameOfProduct + " " + potency + unitOfMeasure +
								"\nWhole Sale Price: " + wholeSalePrice + " \t Retail Price: " + retailPrice + " \t Profit: " + profit()
								+ " \t Remaining Stock: " + remainingStock +
								"\nBatch Number: " + batchNumber + " \t Product Category: " + category + " \t Product Manufacturer: " +
								manufacturer +
								"\nManufacture Date: " + manufactureDate + " \t Expiry Date: " + expiryDate + " \t Total Expiry " +
								"Duration: " + totalExpiryTime() + " Months \t Remaining Expiry Duration: " + remainingExpiryTime() +
								" Months.";
								
		}
		
		public String getSpecificProductBriefDetails() {
				return "Product ID: " + productId + " \t Product Name: " + nameOfProduct + " " + potency + unitOfMeasure +
								" \t Retail Price: " + retailPrice + "/- \t Profit: " + profit() + "/- \t " +
								"Profit Margin: " + profitMargin();
		}

		public void setRemainingStock(long remainingStock) {
				this.remainingStock += remainingStock;
		}
		
		public long totalExpiryTime() {
				return ChronoUnit.MONTHS.between(LocalDate.from(LocalDate.parse(manufactureDate)), LocalDate.parse(expiryDate));
		}
		public long remainingExpiryTime() {
				return ChronoUnit.MONTHS.between(LocalDate.from(LocalDate.now()), LocalDate.parse(expiryDate));
		}

		public String getExpiredBatchDetail() {
				
				String it = null;

				if (remainingExpiryTime() <= 0 ) {
						
						it = productId + ":\n" +
										"\tProduct Name: " + nameOfProduct + "\n" +
										"\tPotency of Product: " + potency + unitOfMeasure + "\n" +
										"\tManufacture Date of Product: " + manufactureDate + "\n" +
										"\tExpiry Date of Product: " + expiryDate + "\n" +
										"\tRemaining Stock of Product: " + remainingStock + "\n";
				}

				return it;
		}
		
		
		public double profit() {
				return retailPrice - wholeSalePrice;
		}
		
		public double profitMargin() {
				return (profit() / retailPrice) * 100;
		}
		
}
