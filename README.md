# Message serialization latency measurement

### Content
* [Description](#description)
* [Json](#json)
* [SBE](#sbe)
* [ProtoBuf](#protobuf)

### Description
It's not a secret that in low-latency systems the message serialization & deserialization takes the most time out of round-trip. Let's be clear, the java code in matching-engine is quite fast, it's message conversion that takes time and both convert object into string and vice versa. And unfortunately we can't skip this step, cause as long as we need to transfer data over network, we have to deal with serialization

### Json


### SBE


### ProtoBuf
