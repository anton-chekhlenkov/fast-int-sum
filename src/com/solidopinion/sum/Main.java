package com.solidopinion.sum;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;

public class Main {
    
    public static void main(String[] args) throws IOException {

//        long startTimeMs = System.currentTimeMillis();
        
    	/* check args */
        if (args.length < 1 || args[0] == null) {
        	System.out.println("You need to set the file name for parsing");
        	return;
        }
        
        /* check file */
        File file = Paths.get(args[0]).toAbsolutePath().toFile();
        if (!file.exists() || !file.isFile()) {
        	System.out.println("Incorrect file name");
        	return;
        }

		long sum = 0L;
		
		try (FileInputStream fileInputStream = new FileInputStream(file); FileChannel channel = fileInputStream.getChannel();) {

			MappedByteBuffer mapBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size());
			mapBuffer.order(ByteOrder.LITTLE_ENDIAN);

			while (mapBuffer.hasRemaining()) {
				sum += (long) mapBuffer.getInt();
			}
        	
		} catch (Exception e) {
			e.printStackTrace(); // TODO: handle exception
		}

		System.out.println(sum);

//        System.out.println("time: " + (System.currentTimeMillis() - startTimeMs) + "ms");

    }
    
}
