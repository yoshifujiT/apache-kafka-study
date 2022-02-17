package com.example.chapter4;

import org.apache.kafka.clients.consumer.*;
// import org.apache.kafka.common.TopicPartition;
import java.util.*;
import java.time.Duration;

public class FirstAppConsumer {

    private static String topicName = "first-app";

    public static void main(String[] args) {

        // 1. KafkaConsumerに必要な設定
        Properties conf = new Properties();
        conf.setProperty("bootstrap.servers", "kafka:9092");
        conf.setProperty("group.id", "FirstAppConsumerGroup");
        // conf.setProperty("enable.auto.commit", "false");
        conf.setProperty("enable.auto.commit", "true");
        conf.setProperty("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        conf.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        // 2. KafkaクラスタからMessageを受信(Consume)するオブジェクトを生成
        Consumer<Integer, String> consumer = new KafkaConsumer<>(conf);

        // 3. 受信(subscribe)するTopicを登録
        List<String> topicList = new ArrayList<>(1);
        topicList.add(topicName);
        consumer.subscribe(topicList);

        for (int count = 0; count < 300; count++) {
            // 4. Messageを受信し、コンソールに表示する
            ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofMillis(1));
            for (ConsumerRecord<Integer, String> record : records) {
                String msgString = String.format("key:%d, value:%s", record.key(), record.value());
                System.out.println(msgString);

                // // 5. 処理が完了したMessageのOffsetをCommitする（auto commitを使わない場合のみ必要）
                // TopicPartition tp = new TopicPartition(record.topic(), record.partition());
                // OffsetAndMetadata oam = new OffsetAndMetadata(record.offset() + 1);
                // Map<TopicPartition, OffsetAndMetadata> commitInfo =
                // Collections.singletonMap(tp, oam);
                // consumer.commitSync(commitInfo);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        // 6. KafkaConsumerをクローズして終了
        consumer.close();
    }
}
