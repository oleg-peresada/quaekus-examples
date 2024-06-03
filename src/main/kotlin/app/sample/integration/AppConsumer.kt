package app.sample.integration

//import jakarta.jms.ConnectionFactory
//import org.apache.activemq.ActiveMQConnectionFactory
//import org.eclipse.microprofile.reactive.messaging.Incoming
//import org.eclipse.microprofile.reactive.messaging.Message
import io.quarkus.smallrye.reactivemessaging.ackSuspending
import io.quarkus.smallrye.reactivemessaging.nackSuspending
import io.smallrye.common.annotation.Identifier
import io.smallrye.mutiny.Multi
import io.smallrye.reactive.messaging.jms.JmsProperties
import io.smallrye.reactive.messaging.jms.OutgoingJmsMessageMetadata
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Produces
import jakarta.inject.Inject
import jakarta.jms.ConnectionFactory
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.delay
import kotlinx.coroutines.future.asDeferred
import org.apache.activemq.ActiveMQConnectionFactory
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.eclipse.microprofile.reactive.messaging.Message
import org.eclipse.microprofile.reactive.messaging.Outgoing
import java.time.Duration
import java.util.concurrent.CompletionStage
import java.util.function.Function


@ApplicationScoped
class ConnectionFactoryBean {

    @ConfigProperty(name = "artemis.username")
    lateinit var username: String

    @ConfigProperty(name = "artemis.password")
    lateinit var password: String

    @Produces
    @Identifier("factory-test")
    fun factory(): ConnectionFactory {

        return ActiveMQConnectionFactory(
            username,
            password,
            "tcp://localhost:61616")
    }
}

@ApplicationScoped
class SendingMessage(
   @Channel("prices") private val emitter: Emitter<String>
) {

    fun sendMessage(message: String): String {
        val metadata = OutgoingJmsMessageMetadata
            .builder()
            .withProperties(JmsProperties.builder().with("my-property", 1).build())
            .build()

        val a = Message.of(message).addMetadata(metadata)
        emitter.send(a)

        return "ok"
    }
}

@ApplicationScoped
class AppConsumer {

//    @Produces
//    @Identifier("my-topic-config")
//    fun options(): AmqpClientOptions {
//        return AmqpClientOptions()
//            .setHost("localhost")
//            .setPort(5672)
//            .setUsername("smallrye")
//            .setPassword("smallrye")
//    }

//    @Produces
//    fun factory(): ConnectionFactory {
//        return ActiveMQConnectionFactory("artemis", "artemis", "tcp://localhost:61616")
//    }

//    @Outgoing("prices")
//    fun generate(): Multi<Message<String>> {
//        val metadata = OutgoingJmsMessageMetadata
//            .builder()
//            .withProperties(JmsProperties.builder().with("my-property", 1).build())
//            .build()
//
//        return Multi.createFrom().ticks().every(Duration.ofSeconds(1))
//            .map { _: Long? ->
//                Message.of("test").addMetadata(metadata)
//            }
//    }

    @Incoming("price")
    fun consume(message: Message<String>): CompletionStage<Void>? {
        println(message.payload)
        //delay(10000000)

        return message.nack(Exception("11"))
    }
}