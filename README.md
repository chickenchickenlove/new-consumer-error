### Related System under test.
- https://cwiki.apache.org/confluence/display/KAFKA/KIP-848%3A+The+Next+Generation+of+the+Consumer+Rebalance+Protocol

### Unexpected behaviour
- Broker is sufficiently slow.
- When a KafkaConsumer is created and immediately subscribes to a topic

If both conditions are met, `Consumer` can potentially never receive `TopicPartition` assignments and become stuck indefinitely.

In case of new broker and new consumer, when consumer are created, consumer background thread send a request to broker. (I guess groupCoordinator Heartbeat request).
In that time, if broker does not load metadata from `__consumer_offset`, broker will start to schedule load metadata.
After broker load metadata completely, consumer background thread think 'this broker is valid group coordinator'. 

However, consumer can send `subscribe` request to broker before `broker` reply about `groupCoordinator HeartBeat Request`. 
In that case, consumer seems to be stuck.  


If both conditions are met, the `Consumer` can potentially never receive `TopicPartition` assignments and may become indefinitely stuck.
In the case of a new `broker` and new `consumer`, when the consumer is created, `consumer background thread` start to send a request to the broker. (I believe this is a `GroupCoordinator Heartbeat request`)
During this time, if the `broker` has not yet loaded metadata from `__consumer_offsets`, it will begin to schedule metadata loading. 
Once the broker has completely loaded the metadata, the `consumer background thread` recognizes this broker as a valid group coordinator.
However, there is a possibility that the `consumer` can send a `subscribe request` to the `broker` before the `broker` has replied to the `GroupCoordinator Heartbeat Request`. 
In such a scenario, the `consumer` appears to be stuck.

You can check this scenario, in the `src/test/java/com/example/MyTest#should_fail_because_consumer_try_to_poll_before_background_thread_get_valid_coordinator`.
If there is no sleep time to wait `GroupCoordinator Heartbeat Request`, `consumer` will be always stuck.
If there is a little sleep time, `consumer` will always receive assignment.