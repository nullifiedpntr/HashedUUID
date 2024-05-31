package me.twisted.whitelist;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;

public class Main {
	public static String uuid;
	
    public static void main(String[] args) {
    	
    	// im aware this is scuffed but this is the only method i could find 
    	String value = "\r\nGet-CimInstance -Query 'SELECT CommandLine FROM Win32_Process WHERE Name LIKE \"%Java%\" AND CommandLine LIKE \"%uuid%\"' | Select-Object -Property CommandLine | Out-String -width 99999\r\n        ";
        
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe");
            processBuilder.redirectErrorStream(true); // Combine stderr and stdout
            
            Process process = processBuilder.start();
            
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
                writer.write(value);
                writer.newLine();
                writer.flush();
            }
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
            
            process.waitFor();
            //System.out.println(output.toString());
            String unparsedUUID = output.toString();
            uuid = sha256(output.toString().split("--uuid")[1].split("--accessToken")[0].trim());
            StringSelection selection = new StringSelection(uuid);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    	
    	new WindowFrame();
    }
    
    public static String sha256(final String base) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes("UTF-8"));
            final StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) 
                  hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
           throw new RuntimeException(ex); 
        }
    }
}