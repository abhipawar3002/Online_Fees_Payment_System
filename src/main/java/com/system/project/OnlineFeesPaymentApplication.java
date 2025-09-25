package com.system.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class OnlineFeesPaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineFeesPaymentApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        printWelcomeMessage();
        checkSystemRequirements();
    }

    private void printWelcomeMessage() {
        System.out.println("\n=========================================");
        System.out.println("  Welcome to Online Fees Payment System");
        System.out.println("  Version: 1.0.0");
        System.out.println("  Developed by: Abhishek Sunil Pawar");
        System.out.println("=========================================\n");
    }

    private void checkSystemRequirements() {
        String javaVersion = System.getProperty("java.version");
        System.out.println("[System Info] Java Version: " + javaVersion);
        
        String osName = System.getProperty("os.name");
        System.out.println("[System Info] OS: " + osName);
        
        long maxMemory = Runtime.getRuntime().maxMemory() / (1024 * 1024);
        System.out.println("[System Info] Max Memory: " + maxMemory + "MB");
        
        System.out.println("\nApplication started successfully!");
    }
}