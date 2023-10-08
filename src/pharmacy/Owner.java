package pharmacy;

import lombok.ToString;

import java.io.Serializable;
import java.util.GregorianCalendar;

@ToString
public class Owner implements Dependable, Streamable, Serializable {
		private String nameOfOwner;
		private String password;
		private String contactNumber;

		private final String[] last10LogInTimeStamps = new String[10];
		private final String[] last10LoginStatusStamps = new String[10];

		public Owner() {
		}
		public Owner(int attempts) {
				
				boolean flag = true;
				String nameOfOwnerToSet;
				
				do {
						System.out.print("\nEnter your name as Owner: ");
						nameOfOwnerToSet = inStr.nextLine();
						
						if (!nameOfOwnerToSet.matches("^(?=.*[A-Za-z])[a-zA-Z\\s]{3,30}$")) {
								System.out.println("\nName must contain at least 3-30 Alphabets, Try Again...");
								continue;
						}
						
						flag = false;
						
				} while (flag);
				
				for (int i = attempts ; i >= 1 ; i--) {
						
						boolean flag1 = true;
						String firstPassword;
						
						do {
								System.out.print("\nEnter the password of the Owner Account: ");
								firstPassword = inStr.nextLine();
								
								if (!firstPassword.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$")) {
										System.out.println("\nPassword must contains at least 8-16 Characters with Digits, Try Again...");
										continue;
								}
								
								flag1 = false;
								
						} while (flag1);
						
						System.out.print("\nConfirm Password: ");
						String secondPassword = inStr.nextLine();
						
						if (firstPassword.equals(secondPassword)) {
								nameOfOwner = nameOfOwnerToSet;
								password = secondPassword;
								break;
						} else {
								System.out.println("\nPasswords don't match, Try Again!\n");
						}
						
						if (i == 1) {
								System.err.println("\nSession terminated, Security at Risk!");
								System.exit(0);
						}
				}
				
				System.out.println("\nOwner Set-up successful!");
		}

		public void updateOwnerName(boolean passwordValidationStatus, String nameOfOwner) {
				if (passwordValidationStatus) {
						this.nameOfOwner = nameOfOwner;
						System.out.println("\nOwner Name updated successfully to \"" + nameOfOwner + "\"");
				}
		}

		public boolean updatePassword(String previousPassword, String newPassword) {
				
				boolean updateStatus = false;
				
				if (password.equals(previousPassword) && newPassword != null) {
						password = newPassword;
						System.out.println("\nPassword Updated Successfully!");
						updateStatus = true;
				}
				
				return updateStatus;
		}

		public void setOrUpdateContactNumber(String contactNumber) {
				this.contactNumber = contactNumber;
				System.out.println("\nContact Number Set-up/Update successful!");
		}
		
		public void updateOwnerName() {
				
				System.out.print("Enter Updated Owner Name: ");
				String inputtedNameOfOwner = inStr.nextLine();
				
				updateOwnerName(validatePassword(), inputtedNameOfOwner);
		}
		
		public void updatePassword() {
				
				String previousPassword;
				String newPassword = null;
				int noOfTries = 0;
				
				do {
						
						if (noOfTries >= 1) {
								System.out.print("Wrong Previous Password, ");
						}
						
						if (noOfTries == 5) {
								System.err.println("\nSession terminated, Security at Risk!");
								System.exit(0);
						}
						
						noOfTries++;
						
						System.out.print("Enter Previous Password: ");
						previousPassword = inStr.nextLine();
						
						System.out.print("Enter New Password: ");
						String newPasswordToSet = inStr.nextLine();
						
						System.out.print("Confirm New Password: ");
						String newConfirmedPasswordToSet = inStr.nextLine();
						
						if (newConfirmedPasswordToSet.equals(newPasswordToSet)) {
								newPassword = newConfirmedPasswordToSet;
						} else {
								System.out.println("New passwords doesn't match.");
						}
				}
				while (!updatePassword(previousPassword, newPassword));
		}
		
		public void setOrUpdateContactNumber() {
				System.out.print("Enter Contact Number: ");
				setOrUpdateContactNumber(inStr.nextLine());
		}
		
		public void validateLogin(Owner owner) {
				
				System.out.println("\nOwner Name: " + nameOfOwner);
				System.out.print("Enter Password: ");
				
				setLastLoginStamps();
				
				for (int attempts = 1; attempts <= 5 && !(inStr.nextLine().equals(this.password));
				     attempts++) {
						
						if (attempts == 5) {
								setLastLoginStamps("Failed!");
								Streamable.serialize(owner);
								System.err.println("\nSession Terminated, Security at Risk!");
								System.exit(0);
						}
						
						System.out.print("Wrong Password, Enter Again: ");
						
				}
				
				setLastLoginStamps("Successful!");
		}
		
		public final boolean validatePassword() {
				
				boolean validatedLoginStatus = false;
				
				System.out.println("\nOwner Name: " + String.format(nameOfOwner).toUpperCase());
				System.out.print("Enter Password: ");
				
				for (int attempts = 1 ; attempts <= 5 && !(validatedLoginStatus = inStr.nextLine().equals(password)) ;
				     attempts++) {
						
						if (attempts == 5) {
								System.err.println("\nSession Terminated, Security at Risk!");
								System.exit(0);
						}
						
						System.out.print("Wrong Password, Enter Again: ");
						
				}
				
				return validatedLoginStatus;
		}
		
		private void setLastLoginStamps() {
				
				for (int i = 9 ; i > 0 ; i--) {
						
						if (last10LogInTimeStamps[i-1] != null) {
								last10LogInTimeStamps[i] = last10LogInTimeStamps[i-1];
								last10LogInTimeStamps[i-1] = null;
						}
						
						if (i == 1) {
								last10LogInTimeStamps[i-1] = String.valueOf(new GregorianCalendar().getTime());
						}
				}
		}
		
		private void setLastLoginStamps(String status) {
				
				for (int i = 9 ; i > 0 ; i--) {
						
						if (last10LoginStatusStamps[i-1] != null) {
								last10LoginStatusStamps[i] = last10LoginStatusStamps[i-1];
								last10LoginStatusStamps[i-1] = null;
						}
						
						if (i == 1) {
								last10LoginStatusStamps[i-1] = status;
						}
				}
		}
		
		public void getLast5LoginStamps() {
				
				if (validatePassword()) {
						
						System.out.println("\nLast 10 Login Stamps;\n");
						
						for (int i = 0 ; i < last10LoginStatusStamps.length ; i++) {
								
								if (last10LoginStatusStamps[i] == null) {
										continue;
								}
								
								System.out.println((i+1) + ".\tAttempt Time:  " + last10LogInTimeStamps[i] + "\t\tStatus:  " +
												last10LoginStatusStamps[i]);
						}
				}
		}
}
