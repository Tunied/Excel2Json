# Excel2Json

## 更新 2019-07-16

![01](imgs/Img5.jpg)

- 加入了对合并后的单元格支持
- Fix 单元格首列类型不为String则无法解析

### 概述

Excel2Json 是将Excel表格直接导出成JSON文件格式的一个小工具

### 使用方法


1. 下载Release文件夹下所有文件,拷贝到本地目录 比如 D:\Tools\Excel2Json 目录
2. 修改Run.bat文件  
`EXCEL_FOLER`: Excel文件所在的目录  
`EXPORT_JSON_FOLDER`:导出的Json目录

3. 也可以在命令行里直接通过 `java -jar Excel2Json-1.1.jar` 转换单独的Excel


```
比如:

java -jar Excel2Json-1.1.jar C:\example.xls D:\out\example.json

```

### Sheet

![01](imgs/Img1.jpg)

#### 不带符号

![01](imgs/Img2.jpg)

不带符号的表单会作为基础模式导出,上图的例子导出的Json结构为:
```
{
  "normal": [
    {
      "name": "xxx",
      "ID": "ID_001",
      "age": 15,
      "info": [
        {
          "arg2": "yy",
          "arg1": "xx"
        },
        {
          "arg2": 23,
          "arg1": 1.1
        }
      ],
      "info2": [
        "ID001",
        "ID002",
        15
      ]
    }
  ]
}
```

- Excel中第一列用于标记是否导出  第一行为二级Key的名称  第二行为二级Key的解析参数

- Sheet的名字为normal,所以Json第一层结构以`normal`做key,里面是一个`Array`  

- 4A,5A,6A标记以`#`开头则当前行不导出. 同理G1也以`#`开头 所以也不导出

- E1,F1均以`@`开头,其内部均按数组方式解析.  
数组中的每个元素用`;`隔开,其内部的子参数用`,`分割  
如果内部只有一个元素可以忽略不写参数(F2就是),此时内部元素会直接Push到数组中

#### #号开头

以`#`开头表示当前Sheet不导出,同理在Sheet内部也是这样. 一行或者一列以`#`开头均不导出


#### $号开头

![01](imgs/Img3.jpg)

以`$`开头,则内部仅读取前四列中的信息(从第二行开始读起,第一行不读取),其中


- 第一列: 标记当前行是否读取
- 第二列: 内部Key
- 第三列: 内部Key的解析参数
- 第四列: 具体的Value值


```
{
  "eranInfo": {
    "gender": 1,
    "name": "Eran",
    "icon": "icon.png",
    "ID": "function test(){alert(\"Called\");}",
    "age": 18,
    "info": [
      {
        "arg2": "yy",
        "arg1": "xx"
      },
      {
        "arg2": 23,
        "arg1": 1.1
      }
    ],
    "info2": [
      "ID001",
      "ID002",
      15
    ]
  }
}
```

#### !号开头

![01](imgs/Img4.jpg)

以`!`开头的Excel一般用于多语言文件. 程序导出逻辑同`$`开头的Sheet,不过会把每一列单独导出一个Json文件.
比如当前Excel文件名是I18N,则上图中的表会被分别导出到`I18N_zh_CN.json`和`I18N_en_US.json`

其导出的Json结构为:
```
{
  "I18N": {
    "Common_OK": "好",
    "TF_GiftBtn": "礼物",
    "info": [
      {
        "arg2": "yy",
        "arg1": "xx"
      },
      {
        "arg2": 23,
        "arg1": 1.1
      }
    ],
    "info2": [
      "ID001",
      "ID002",
      15
    ]
  }
}
```

其内部的一级Key,上面Demo中的`I18N`就是Excel的文件名.




