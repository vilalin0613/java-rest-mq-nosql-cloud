package com.example.vila.repository.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.WriteType;
import com.datastax.driver.core.exceptions.DriverException;
import com.datastax.driver.core.policies.RetryPolicy;

/**
 * Cassandra data source.
 * 
 * Retry on same host: ReadTimeOut, WriteTimeOut, Unavailable
 * Retry on next host: RequestError (ex: OperationTimedOut, ServerError, ConnectionException, OverloadedException)
 *
 * @author  Vila Lin<vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
public class ConfirmRetryPolicy implements RetryPolicy {

	private int retryLimit = 1;

	public ConfirmRetryPolicy(int retryLimit) {
		this.retryLimit = retryLimit;
	}

	@Override
	public RetryDecision onReadTimeout(Statement statement, ConsistencyLevel cl, int requiredResponses,
			int receivedResponses, boolean dataRetrieved, int nbRetry) {

		if (nbRetry < retryLimit) {
			return RetryDecision.retry(cl);
		} else {
			return RetryDecision.rethrow();
		}
	}

	@Override
	public RetryDecision onWriteTimeout(Statement statement, ConsistencyLevel cl, WriteType writeType, int requiredAcks,
			int receivedAcks, int nbRetry) {
		
		// Such write should not be replayed to avoid over-counting
		if (null != writeType && writeType.equals(WriteType.COUNTER)) {
			return RetryDecision.ignore();
		}

		if (nbRetry < retryLimit) {
			return RetryDecision.retry(cl);
		} else {
			return RetryDecision.rethrow();
		}
	}

	@Override
	public RetryDecision onUnavailable(Statement statement, ConsistencyLevel cl, int requiredReplica, int aliveReplica,
			int nbRetry) {
		
		if (nbRetry < retryLimit) {
			return RetryDecision.retry(cl);
		} else {
			return RetryDecision.rethrow();
		}
	}

	@Override
	public RetryDecision onRequestError(Statement statement, ConsistencyLevel cl, DriverException e, int nbRetry) {

		return RetryDecision.tryNextHost(cl);
	}

	@Override
	public void init(Cluster cluster) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}