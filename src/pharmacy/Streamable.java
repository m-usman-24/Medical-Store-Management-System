package pharmacy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public interface Streamable {
		
		String FILE_IS_NOT_WRITEABLE = "File is not Writeable.";
		
		static String getCustomSystemDirectory() {
				
				String customPath = null;
				String tempCustomPath = System.getenv("SystemDrive") + "\\ProgramData\\Medical Store";
				
				File customDir = new File(tempCustomPath);
				if (customDir.isDirectory() || customDir.mkdir()) {
						customPath = tempCustomPath;
				}
				
				return customPath;
		}
		
		static String getCustomExportDirectory() {
				
				String exportDirectory = null;
				String tempExportDirectory = getCustomSystemDirectory() + "\\Exports";
				
				File exportDir = new File(tempExportDirectory);
				
				if (exportDir.isDirectory() || exportDir.mkdir()) {
						exportDirectory = tempExportDirectory;
				}
				
				return exportDirectory;
		}
		static void serialize(Object object) {
				
				String fileName = null;
				
				if (object instanceof MedicalStore) {
						fileName = "\\SerializedMedicalStore.bin";
				} else if (object instanceof Owner) {
						fileName = "\\SerializedOwner.bin";
				}
				
				try (ObjectOutputStream os =
								     new ObjectOutputStream(Files.newOutputStream(Paths.get(getCustomSystemDirectory() +
												     fileName)))) {
						
						os.writeObject(object);
						os.flush();
						
				} catch (IOException e) {
						
						throw new RuntimeException(e);
						
				}
		}
		
		static Object deserialize(Object object) {
				
				Object instance = null;
				String fileName = null;
				
				if (object instanceof MedicalStore) {
						fileName = "\\SerializedMedicalStore.bin";
				} else if (object instanceof Owner) {
						fileName = "\\SerializedOwner.bin";
				}
				try (ObjectInputStream is =
								     new ObjectInputStream(Files.newInputStream(Paths.get(getCustomSystemDirectory() +
												     fileName)))) {
						
						instance = is.readObject();
						
				} catch (IOException | ClassNotFoundException e) {
						e.printStackTrace();
				}
				
				return instance;
		}
		
		static void cleanUp(Path path) throws IOException {
				Files.delete(path);
		}
		
		static void checkFileStatus(File file) throws IOException {
				
				if (!file.exists() && !file.createNewFile()) {
						System.err.println("Error Creating File.");
				}
		}
		
		static String exportProductInventory(Map<String, Product> listOfProducts) throws IOException {
				
				if (listOfProducts.isEmpty()) {
						System.out.println("\n Nothing to Export, Product Inventory is Empty.");
						return null;
				}
				
				String exportDirectory = getCustomExportDirectory();
				
				File file = new File(exportDirectory + "\\Product Inventory.csv");
				
				checkFileStatus(file);
				
				if (!file.canWrite()) {
						System.err.println(FILE_IS_NOT_WRITEABLE);
						return null;
				}
				
				if (file.length() > 0) {
						cleanUp(file.toPath());
				}
				
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
						
						bw.write("Product ID,Name,WholeSale,Retail,Profit,Profit %age,Stock,MFG Date,Expiry Date, Expiry Left");
						bw.newLine();
						
						for (Product product : listOfProducts.values()) {
								
								bw.append(String.format("%s,%s %.1f%s,%.2f/-,%.2f/-,%.2f/-,%.2f%%,%d,%s,%s,%sM", product.getProductId(),
												product.getNameOfProduct(), product.getPotency(), product.getUnitOfMeasure(),
												product.getWholeSalePrice(), product.getRetailPrice(), product.profit(),
												product.profitMargin(), product.getRemainingStock(),product.getManufactureDate(),
												product.getExpiryDate(), product.remainingExpiryTime()));
								bw.newLine();
						}
						
						bw.flush();
						
				} catch (IOException e) {
						throw new RuntimeException(e);
				}
				
				return file.getAbsolutePath();
		}
		
		static String exportCustomerList(Map<String, Customer> listOfCustomers) throws IOException {
				
				if (listOfCustomers.isEmpty()) {
						System.out.println("\n Nothing to Export, Customer List is Empty.");
						return null;
				}
				
				String exportDirectory = getCustomExportDirectory();
				
				File file = new File(exportDirectory + "\\Customer List.csv");
				
				checkFileStatus(file);
				
				if (!file.canWrite()) {
						System.err.println(FILE_IS_NOT_WRITEABLE);
						return null;
				}
				
				if (file.length() > 0) {
						cleanUp(file.toPath());
				}
				
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
						
						bw.write("Customer ID,Name,Patient,Prescriber,Disease,Dues");
						bw.newLine();
						
						for (Customer customer : listOfCustomers.values()) {
								
								bw.append(String.format("%s,%s,%s,%s,%s,%.2f/-", customer.getCustomerId(),
												customer.getNameOfCustomer(), customer.getPatientName(), customer.getNameOfPrescriber(),
												customer.getDiagnosedDisease(), customer.getCustomerDebt()));
								bw.newLine();
						}
						
						bw.flush();
						
				} catch (IOException e) {
						throw new RuntimeException(e);
				}
				
				return file.getAbsolutePath();
		}
		
		static String exportBills(Map<String, Customer> listOfCustomers) throws IOException {
				
				if (listOfCustomers.isEmpty()) {
						System.out.println("\n Nothing to Export, There is no customer to Export Bill for.");
						return null;
				}
				
				String exportDirectory = getCustomExportDirectory();
				
				File file = new File(exportDirectory + "\\Bills.csv");
				
				checkFileStatus(file);
				
				if (!file.canWrite()) {
						System.err.println(FILE_IS_NOT_WRITEABLE);
						return null;
				}
				
				if (file.length() > 0) {
						cleanUp(file.toPath());
				}
				
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
						
						bw.write("Bill ID,Wholesale,Retail,profit %age,Bill After Discount,Profit %age After Discount,Paid," +
										"Discount %age");
						bw.newLine();
						
						for (Customer customer : listOfCustomers.values()) {
								
								if (customer.getBills().isEmpty()) {
										continue;
								}
								
								for (Bill bill : customer.getBills().values()) {
										
										bw.append(String.format("%s,%.2f/-,%.2f/-,%.2f%%,%.2f/-,%.2f%%,%.2f/-,%.2f%%", bill.getBillId(),
														bill.getTotalWholeSaleCost(), bill.getTotalRetailCost(), bill.getTotalProfitMargin(),
														bill.getBillAmountAfterDiscount(), bill.getTotalProfitMarginAfterDiscount(),
														bill.getPaidAmount(), bill.getDiscountPercentage()));
										bw.newLine();
								}
						}
						
						bw.flush();
						
				} catch (IOException e) {
						throw new RuntimeException(e);
				}
				
				return file.getAbsolutePath();
		}
}
