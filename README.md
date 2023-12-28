# Message serialization latency measurement

### Content
* [Description](#description)
* [Json](#json)
* [SBE](#sbe)
* [ProtoBuf](#protobuf)
* [Test Results](#test-results)

### Description
It's not a secret that in low-latency systems the message serialization & deserialization takes the most time out of round-trip. Let's be clear, the java code in matching-engine is quite fast, it's message conversion that takes time and both convert object into string and vice versa. And unfortunately we can't skip this step, cause as long as we need to transfer data over network, we have to deal with serialization.
There are 2 types of messages:
* text `XML/JSON/CUSTOM` - here we convert our object into human-readable strings. The big advantage that any human can read raw message and understand it content. The disadvantage in low-latency system, is that it's very slow process.
* binary `SBE/GPB` - here we convert out object into binary data, it's not human-readable, if you try to open such file with any text editor, you will get some gibberish unreadable string. But the main advantage compare to text formats, it's conversion speed. In this project we will take a look into different formats and compare their performance end-to-end.

### Json


### SBE
Binary data is faster then text: json parse is very slow, first you parse bytes into text, then you parse text into json object, by comparing values one-by-one character. While in binary either protobuf or SBE, you already has a pre-compiled message structure and when you parse binary, you know where each value from start to end, and quickly parse it => add to document.
SBE is faster then other binary formats: since in SBE we first store fixed-sized data like int/long & enums, it's fast-moving reads, and show better performance.
```bash
# build your schema manually using following command
java -jar -Dsbe.output.dir=target/generated-sources ~/.m2/repository/uk/co/real-logic/sbe-all/1.30.0/sbe-all-1.30.0.jar src/main/resources/sbe-schema.xml 

# first install protobuf compiler
 brew install protobuf
# check version
protoc --version
# generate proto file
protoc -I=src/main/resources --java_out=target/generated-sources schema.proto
```

### ProtoBuf

### Test Results
Below are test results from [JmhPerformanceTest](/src/test/java/com/exchange/serialization/performance/JmhPerformanceTest.java)
```
Benchmark                                   Mode  Cnt   Score    Error  Units
JmhPerformanceTest.customTextSerialization  avgt    5  ≈ 10⁻³           ms/op
JmhPerformanceTest.jsonSerialization        avgt    5   0.001 ±  0.001  ms/op
JmhPerformanceTest.orderGeneration          avgt    5  ≈ 10⁻⁵           ms/op
JmhPerformanceTest.protobufSerialization    avgt    5  ≈ 10⁻⁴           ms/op
JmhPerformanceTest.sbeSerialization         avgt    5  ≈ 10⁻⁶           ms/op
```