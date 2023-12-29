package com.exchange.serialization.performance;

import com.exchange.serialization.MockData;
import com.exchange.serialization.helper.json.JsonSerializerImpl;
import com.exchange.serialization.model.Order;
import com.exchange.serialization.serializer.CustomTextSerializer;
import com.exchange.serialization.serializer.JsonOrderSerializer;
import com.exchange.serialization.serializer.ProtobufSerializer;
import com.exchange.serialization.serializer.SbeOrderSerializer;
import com.exchange.serialization.serializer.Serializer;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 2, time = 5)
@Measurement(iterations = 5, time = 10)
public class JmhPerformanceTest {

  private Serializer jsonOrderSerializer;
  private Serializer customOrderSerializer;
  private Serializer protobufOrderSerializer;
  private Serializer sbeOrderSerializer;
  private Order order = MockData.buyLimitOrder();

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(JmhPerformanceTest.class.getSimpleName())
        .build();
    new Runner(opt).run();
  }

  @Setup(Level.Iteration)
  public void setUp() {
    jsonOrderSerializer = new JsonOrderSerializer(new JsonSerializerImpl());
    customOrderSerializer = new CustomTextSerializer();
    protobufOrderSerializer = new ProtobufSerializer();
    sbeOrderSerializer = new SbeOrderSerializer();
  }

  @Benchmark
  public void customTextSerialization(Blackhole blackhole) {
    byte[] serialized = customOrderSerializer.serialize(order);
    blackhole.consume(serialized);
    blackhole.consume(customOrderSerializer.deserialize(serialized));
  }

  @Benchmark
  public void jsonSerialization(Blackhole blackhole) {
    byte[] serialized = jsonOrderSerializer.serialize(order);
    blackhole.consume(serialized);
    blackhole.consume(jsonOrderSerializer.deserialize(serialized));
  }

  @Benchmark
  public void protobufSerialization(Blackhole blackhole) {
    byte[] serialized = protobufOrderSerializer.serialize(order);
    blackhole.consume(serialized);
    blackhole.consume(protobufOrderSerializer.deserialize(serialized));
  }

  @Benchmark
  public void sbeSerialization(Blackhole blackhole) {
    byte[] serialized = sbeOrderSerializer.serialize(order);
    blackhole.consume(serialized);
    blackhole.consume(sbeOrderSerializer.deserialize(serialized));
  }
}