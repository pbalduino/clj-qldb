package pbalduino.cljqldb.helper;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.qldbsession.AmazonQLDBSessionClientBuilder;

import software.amazon.qldb.PooledQldbDriver;
import software.amazon.qldb.QldbDriver;
import software.amazon.qldb.QldbSession;
import software.amazon.qldb.exceptions.QldbClientException;

public final class Connection {
  private Connection() {}

  public static PooledQldbDriver createQldbDriver(final String ledgerName, final int retries) {
    AmazonQLDBSessionClientBuilder builder = AmazonQLDBSessionClientBuilder.standard();

    return PooledQldbDriver.builder()
            .withLedger(ledgerName)
            .withRetryLimit(retries)
            .withSessionClientBuilder(builder)
            .build();
  }
}
