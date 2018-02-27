[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/cn.javaer/aliyun-sms/badge.svg)](https://maven-badges.herokuapp.com/maven-central/cn.javaer/aliyun-sms)
[![Build Status](https://travis-ci.org/cn-src/aliyun-sms.svg?branch=master)](https://travis-ci.org/cn-src/aliyun-sms)
[![Libraries.io for GitHub](https://img.shields.io/librariesio/github/cn-src/aliyun-sms.svg)](https://libraries.io/github/cn-src/aliyun-sms)
# aliyun-sms
阿里云 SMS 短信 Java SDK 封装

# 使用
1. 添加依赖
``` xml
<dependency>
    <groupId>cn.javaer</groupId>
    <artifactId>aliyun-sms</artifactId>
    <version>1.0.0</version>
</dependency>
```

2. 样例
```java
SmsClient smsClient = new SmsClient(accessKeyId, accessKeySecret);
SmsTemplate smsTemplate = SmsTemplate.builder()
            .signName(signName)
            .templateCode(templateCode)
            .addTemplateParam("code", "123456")
            .phoneNumbers(phoneNumber)
            .build();
smsClient.send(smsTemplate);            
```

# spring boot 集成
1. 添加依赖
``` xml
<dependency>
    <groupId>cn.javaer</groupId>
    <artifactId>aliyun-spring-boot-starter-sms</artifactId>
    <version>1.0.0</version>
</dependency>
```

2. 样例
```java
    @Autowired
    private SmsClient smsClient;
```

[官方文档:https://help.aliyun.com/document_detail/55284.html?spm=5176.8195934.1001856.3.5cd64183fNqodO](https://help.aliyun.com/document_detail/55284.html?spm=5176.8195934.1001856.3.5cd64183fNqodO)
