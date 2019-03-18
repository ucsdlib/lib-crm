package edu.ucsd.library.crm.apps;

/*
 * 
 * transform_employee_file.java
 *
 */

import java.io.File;
import java.text.Format;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import edu.ucsd.library.shared.Mail;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class transform_employee_file {
    public static boolean isValid = false;
  
	/** Creates new create_employee_file */
	public transform_employee_file() {
	}

	/**
	 * Method to generate the filenames of the resulting CSV file
	 * @return String - The filename
	 */
	public static String createCsvFileName() {

		Format formatter;
		formatter = new SimpleDateFormat("yyyy-MMM-dd");
		String dateString = formatter.format(new java.util.Date());
		return ("inc-employees-" + dateString + ".csv");
	}

	/**
	 * Method to generate the filenames of the resulting CSV file
	 * @return String - The filename
	 */
	public static String createFullCsvFileName() {

		Format formatter;
		formatter = new SimpleDateFormat("yyyy-MMM-dd");
		String dateString = formatter.format(new java.util.Date());
		return ("full-employees-" + dateString + ".csv");
	}
	
	/**
	* @param args the command line arguments
	* args[0] = destination directory
	* args[1] = properties directory
	*/
	public static void main(String args[]) {

		if ((args == null) || (args.length < 2)) {
			System.out.println(
				"\nSyntax: java transform_employee_file [destination directory] [properties dir]");
		} else {
			String org_name_mapping = args[1] + File.separator + "org_name_mapping.properties";
			try {
				File tmp = new File(args[0] + "all_transform_employee.txt");
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		        String modifiedDatePart = null;
		        if(tmp.exists())
		            modifiedDatePart = formatter.format(tmp.lastModified());
		        
		        Calendar c =  Calendar.getInstance();
		        String todayDatePart = formatter.format(c.getTime());

                isValid = full_employee.grabData(org_name_mapping, args[0] + File.separator + "all_transform_employee.txt", 
                    args[0] + File.separator + "all_active_employee.txt", args[0] + File.separator + "missing_mapping.txt");
				if(isValid) {
                    makecsv_employee.makeCsv(
							args[1],
							args[0] + File.separator + "all_transform_employee.txt",
							args[0] + File.separator + createFullCsvFileName());
				
				    //Create incremental employees file
				
				    full_employee.grabData(org_name_mapping, args[0] + File.separator + "inc_transform_employee.txt", 
                        args[0] + File.separator + "inc_all_employee.txt", args[0] + File.separator + "missing_mapping.txt");
				    makecsv_employee.makeCsv(
                        args[1],
                        args[0] + File.separator + "inc_transform_employee.txt",
                        args[0] + File.separator + createCsvFileName());
				} else {
				    String emailAddress = "mstuart@ucsd.edu,ABarsh@ucsd.edu,hjsyoo@ucsd.edu,tchu@ucsd.edu";
                    String[] email = emailAddress.split(",");
	                String emailcontent = full_employee.getEmailContent(args[0] + File.separator + "missing_mapping.txt");
	                String subject = "Lib-CRM Data Extract: New appointment titles or departments detected";
	                try {
	                     Mail.sendMail("no-reply@ucsd.edu", email, subject, emailcontent, "text/html; charset=utf-8", "smtp.ucsd.edu" );
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }		  
				}
			} catch (Exception e) {
			    e.printStackTrace();
			}					
		}
		System.out.println("end");
	}
}
