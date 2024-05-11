### Log
```
21:31:27.876 [main] INFO org.apache.kafka.common.telemetry.internals.KafkaMetricsCollector -- initializing Kafka metrics collector
21:31:27.905 [main] INFO org.apache.kafka.common.utils.AppInfoParser -- Kafka version: 3.7.0
21:31:27.905 [main] INFO org.apache.kafka.common.utils.AppInfoParser -- Kafka commitId: 2ae524ed625438c5
21:31:27.905 [main] INFO org.apache.kafka.common.utils.AppInfoParser -- Kafka startTimeMs: 1715430687905
21:31:27.906 [main] INFO org.apache.kafka.clients.consumer.internals.AsyncKafkaConsumer -- [Consumer clientId=consumer-hello-1, groupId=hello] Subscribed to topic(s): hello

i :0
i :1
ApplicationEvent{type=SUBSCRIPTION_CHANGE}
PollApplicationEvent{type=POLL, pollTimeMs=1715430687913}
[ApplicationEvent{type=SUBSCRIPTION_CHANGE}, PollApplicationEvent{type=POLL, pollTimeMs=1715430687913}]
i :2
FindCoordinatorRequestData(key='hello', keyType=0, coordinatorKeys=)

PollApplicationEvent{type=POLL, pollTimeMs=1715430689021}
PollApplicationEvent{type=POLL, pollTimeMs=1715430693556}
FindCoordinatorRequestData(key='hello', keyType=0, coordinatorKeys=)
FindCoordinatorRequestData(key='hello', keyType=0, coordinatorKeys=)
FindCoordinatorRequestData(key='hello', keyType=0, coordinatorKeys=)
FindCoordinatorRequestData(key='hello', keyType=0, coordinatorKeys=)
FindCoordinatorRequestData(key='hello', keyType=0, coordinatorKeys=)
21:31:33.684 [consumer_background_thread] INFO org.apache.kafka.clients.Metadata -- [Consumer clientId=consumer-hello-1, groupId=hello] Cluster ID: L_cIY_FVT_qscx4U1Ctk4A
FindCoordinatorRequestData(key='hello', keyType=0, coordinatorKeys=)
21:31:33.826 [consumer_background_thread] INFO org.apache.kafka.clients.consumer.internals.CoordinatorRequestManager -- [Consumer clientId=consumer-hello-1, groupId=hello] Discovered group coordinator Coordinator(key='hello', nodeId=0, host='127.0.0.1', port=12000, errorCode=0, errorMessage='')
make heartbeat request! <---
ConsumerGroupHeartbeatRequestData(groupId='hello', memberId='', memberEpoch=0, instanceId=null, rackId=null, rebalanceTimeoutMs=300000, subscribedTopicNames=[hello], serverAssignor=null, topicPartitions=)
Wait requestInFlight, not make new heartbeatRequest.
ConsumerGroupHeartbeatRequestData(groupId='hello', memberId='', memberEpoch=0, instanceId=null, rackId=null, rebalanceTimeoutMs=300000, subscribedTopicNames=[hello], serverAssignor=null, topicPartitions=)
Wait requestInFlight, not make new heartbeatRequest.
ConsumerGroupHeartbeatRequestData(groupId='hello', memberId='', memberEpoch=0, instanceId=null, rackId=null, rebalanceTimeoutMs=300000, subscribedTopicNames=[hello], serverAssignor=null, topicPartitions=)
Wait requestInFlight, not make new heartbeatRequest.
ConsumerGroupHeartbeatRequestData(groupId='hello', memberId='', memberEpoch=0, instanceId=null, rackId=null, rebalanceTimeoutMs=300000, subscribedTopicNames=[hello], serverAssignor=null, topicPartitions=)
Wait requestInFlight, not make new heartbeatRequest.
ConsumerGroupHeartbeatRequestData(groupId='hello', memberId='', memberEpoch=0, instanceId=null, rackId=null, rebalanceTimeoutMs=300000, subscribedTopicNames=[hello], serverAssignor=null, topicPartitions=)
Wait requestInFlight, not make new heartbeatRequest.
21:31:33.943 [consumer_background_thread] INFO org.apache.kafka.clients.consumer.internals.HeartbeatRequestManager -- [Consumer clientId=consumer-hello-1, groupId=hello] GroupHeartbeatRequest failed because the group coordinator Optional[127.0.0.1:12000 (id: 2147483647 rack: null)] is incorrect. Will attempt to find the coordinator again and retry in 0ms: This is not the correct coordinator.
21:31:33.943 [consumer_background_thread] INFO org.apache.kafka.clients.consumer.internals.CoordinatorRequestManager -- [Consumer clientId=consumer-hello-1, groupId=hello] Group coordinator 127.0.0.1:12000 (id: 2147483647 rack: null) is unavailable or invalid due to cause: This is not the correct coordinator.. Rediscovery will be attempted.
FindCoordinatorRequestData(key='hello', keyType=0, coordinatorKeys=)
21:31:33.969 [consumer_background_thread] INFO org.apache.kafka.clients.consumer.internals.CoordinatorRequestManager -- [Consumer clientId=consumer-hello-1, groupId=hello] Discovered group coordinator Coordinator(key='hello', nodeId=0, host='127.0.0.1', port=12000, errorCode=0, errorMessage='')
Wait requestInFlight, not make new heartbeatRequest.


i :3
Wait requestInFlight, not make new heartbeatRequest. <--- Still wait inFlightRequest of HeartBeat which timer is expired.
PollApplicationEvent{type=POLL, pollTimeMs=1715430694558}
Wait requestInFlight, not make new heartbeatRequest. <--- Still wait inFlightRequest of HeartBeat which timer is expired.

...


i :17
Wait requestInFlight, not make new heartbeatRequest. <--- Still wait inFlightRequest of HeartBeat which timer is expired.
PollApplicationEvent{type=POLL, pollTimeMs=1715430708638}
Wait requestInFlight, not make new heartbeatRequest. <--- Still wait inFlightRequest of HeartBeat which timer is expired.
... 

i :18
Wait requestInFlight, not make new heartbeatRequest. <--- Still wait inFlightRequest of HeartBeat which timer is expired.
PollApplicationEvent{type=POLL, pollTimeMs=1715430709640}
Wait requestInFlight, not make new heartbeatRequest. <--- Still wait inFlightRequest of HeartBeat which timer is expired.
...

i :19
Wait requestInFlight, not make new heartbeatRequest. <--- Still wait inFlightRequest of HeartBeat which timer is expired. 
PollApplicationEvent{type=POLL, pollTimeMs=1715430710643}
Wait requestInFlight, not make new heartbeatRequest. <--- Still wait inFlightRequest of HeartBeat which timer is expired. 
Wait requestInFlight, not make new heartbeatRequest. <--- Still wait inFlightRequest of HeartBeat which timer is expired.
retry consume
21:31:51.644 [main] INFO org.apache.kafka.clients.consumer.internals.AsyncKafkaConsumer -- [Consumer clientId=consumer-hello-1, groupId=hello] Subscribed to topic(s): hello
Wait requestInFlight, not make new heartbeatRequest. <--- Still wait inFlightRequest of HeartBeat which timer is expired.
ApplicationEvent{type=SUBSCRIPTION_CHANGE}           <--- !!!!! Strart retry to subscribe same topic. but never recieved assignement.
Wait requestInFlight, not make new heartbeatRequest. <--- Still wait inFlightRequest of HeartBeat which timer is expired.
... 
Wait requestInFlight, not make new heartbeatRequest. <--- Still wait inFlightRequest of HeartBeat which timer is expired.


i :20
...
Wait requestInFlight, not make new heartbeatRequest.
PollApplicationEvent{type=POLL, pollTimeMs=1715430720663}
...
Wait requestInFlight, not make new heartbeatRequest.

i :21
...
Wait requestInFlight, not make new heartbeatRequest.
PollApplicationEvent{type=POLL, pollTimeMs=1715430720663}
...
Wait requestInFlight, not make new heartbeatRequest.

i : 22
...
Wait requestInFlight, not make new heartbeatRequest.
PollApplicationEvent{type=POLL, pollTimeMs=1715430720663}
...
Wait requestInFlight, not make new heartbeatRequest.

i : 23
...
Wait requestInFlight, not make new heartbeatRequest.
PollApplicationEvent{type=POLL, pollTimeMs=1715430720663}
...
Wait requestInFlight, not make new heartbeatRequest.

i :24
...
Wait requestInFlight, not make new heartbeatRequest.
PollApplicationEvent{type=POLL, pollTimeMs=1715430720663}
...
Wait requestInFlight, not make new heartbeatRequest. <--- Still wait inFlightRequest of HeartBeat which timer is expired.
...
```
