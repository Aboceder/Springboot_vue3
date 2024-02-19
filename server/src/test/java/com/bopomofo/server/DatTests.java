package com.bopomofo.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@SpringBootTest
class DatTests {

    @Test
    void contextLoads() {
    }

    @Test
    void datTest() throws FileNotFoundException {
        File department = new File("/Users/bopomofo/Downloads/department.DAT");
        File txl = new File("/Users/bopomofo/Downloads/txl.DAT");
//        Scanner scanner = new Scanner(txl);
        Scanner scanner = new Scanner(department);

        // 跳过第一行
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        int count = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (!scanner.hasNextLine()) {
                break; // 如果是最后一行，则跳出循环
            }
            // 使用split("~", -1)中的-1参数，表示允许任意数量的分割，并且保留末尾的空字符串
            String[] data = line.split(",")[0].split("~", -1);
            for (String item : data) {
                System.out.println(item);
            }
            //System.out.println("数组长度：" + data.length);
            count++;
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        System.out.println("count = " + count);
    }

}
