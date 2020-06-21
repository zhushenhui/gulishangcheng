package excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zsh
 * @date 2020/6/21
 */
public class TestEasyExcel {

    public static void main(String[] args) {

//        // 实现写操作
//
//        // 1、 导入文件夹地址和名称
//        String filename = "E:/write.xlsx";
//
//        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getData());

        // 实现读操作

        //
        String fileName = "E:/write.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, DemoData.class, new ExcelListener()).sheet().doRead();
    }

    public static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("Lucy" + i);
            list.add(data);
        }
        return list;
    }
}
