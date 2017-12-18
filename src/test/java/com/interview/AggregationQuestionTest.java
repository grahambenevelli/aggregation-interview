package com.interview;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.interview.rectangle.AggregationQuestion;
import com.interview.rectangle.Metric;
import com.interview.rectangle.User;
import com.interview.rectangle.impl.BasicMetric;
import com.interview.rectangle.impl.BasicUser;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AggregationQuestionTest {
	
	private AggregationQuestion aggregationQuestion;
	
	@Before
	public void setup() {
		aggregationQuestion = new AggregationQuestion();
	}

	/**
	 * users
 	 * id, name
	 * [ 1, 'bob']
	 * [ 2, 'sally’]
	 * ...
 	 */

	/**
 	 * metrics
	 * user_id, name, value
	 * [ 1, ‘likes', 10]
	 * [ 1, ‘views', 98]
	 * [ 1, ‘clicks', 390]
	 * [ 2, ‘likes', 56]
	 * [ 2, ‘views', 76]
	 * [ 2, ‘clicks', 23]
	 * ...
	 */

	/**
	 * [ [ 2, ‘bob'] -> [clicks -> 23, likes -> 56], [ 1, ‘sally'] -> [clicks -> 390, likes -> 108]
	 */

	@Test
	public void testAggregate() throws Exception {
		User bob = new BasicUser(1, "bob");
		User sally = new BasicUser(2, "sally");

		List<User> users = Lists.newArrayList(bob, sally);
		
		List<Metric> metric = Lists.<Metric>newArrayList(
				new BasicMetric(1, "likes", 10),
				new BasicMetric(1, "views", 98),
				new BasicMetric(1, "clicks", 390),
				new BasicMetric(2, "likes", 56),
				new BasicMetric(2, "views", 76),
				new BasicMetric(2, "likes", 23)
		);

		Map<User, Map<String, Long>> actual = this.aggregationQuestion.aggregate(metric, users);
		
		Map<User, Map<String, Long>> expected = ImmutableMap.<User, Map<String, Long>>builder()
				.put(bob, ImmutableMap.<String, Long>builder()
						.put("likes", 10L)
						.put("views", 98L)
						.put("clicks", 390L)
						.build())
				.put(sally, ImmutableMap.<String, Long>builder()
						.put("likes", 79L)
						.put("views", 76L)
						.build())
				.build();

		assertNotNull(actual);
		assertTrue(actual.containsKey(bob));
		assertTrue(actual.containsKey(sally));
		assertTrue(actual.get(bob).containsKey("likes"));
		assertTrue(actual.get(sally).containsKey("likes"));
		assertEquals(expected, actual);
	}

	@Test
	public void testAggregate2() throws Exception {
		List<User> users = Lists.<User>newArrayList(
				new BasicUser(1, "bob"),
				new BasicUser(2, "sally")
		);

		List<Metric> metric = Lists.<Metric>newArrayList(
				new BasicMetric(1, "likes", 10),
				new BasicMetric(1, "views", 98),
				new BasicMetric(1, "clicks", 390),
				new BasicMetric(2, "likes", 56),
				new BasicMetric(2, "views", 76),
				new BasicMetric(2, "clicks", 23)
		);

		Map<User, Map<String, Long>> actual = this.aggregationQuestion.aggregate(metric, users);

		Map<User, Map<String, Long>> expected = ImmutableMap.<User, Map<String, Long>>builder()
				.put(new BasicUser(1, "bob"), ImmutableMap.<String, Long>builder()
						.put("likes", 10l)
						.put("views", 98l)
						.put("clicks", 390l)
						.build())
				.put(new BasicUser(2, "sally"), ImmutableMap.<String, Long>builder()
						.put("likes", 56l)
						.put("views", 76l)
						.put("clicks", 23l)
						.build())
				.build();

		assertNotNull(actual);
		assertTrue(actual.containsKey(new BasicUser(1, "bob")));
		assertTrue(actual.containsKey(new BasicUser(2, "sally")));
		assertTrue(actual.get(new BasicUser(1, "bob")).containsKey("likes"));
		assertTrue(actual.get(new BasicUser(2, "sally")).containsKey("likes"));
		assertEquals(expected, actual);
	}

	@Test
	public void testAggregateDuplicate() throws Exception {
		List<User> users = Lists.<User>newArrayList(
				new BasicUser(1, "bob"),
				new BasicUser(2, "sally")
		);

		List<Metric> metric = Lists.<Metric>newArrayList(
				new BasicMetric(1, "likes", 10),
				new BasicMetric(1, "likes", 98),
				new BasicMetric(1, "clicks", 390),
				new BasicMetric(2, "likes", 56),
				new BasicMetric(2, "views", 76),
				new BasicMetric(2, "clicks", 23)
		);

		Map<User, Map<String, Long>> actual = this.aggregationQuestion.aggregate(metric, users);

		Map<User, Map<String, Long>> expected = ImmutableMap.<User, Map<String, Long>>builder()
				.put(new BasicUser(1, "bob"), ImmutableMap.<String, Long>builder()
						.put("likes", 108l)
						.put("clicks", 390l)
						.build())
				.put(new BasicUser(2, "sally"), ImmutableMap.<String, Long>builder()
						.put("likes", 56l)
						.put("views", 76l)
						.put("clicks", 23l)
						.build())
				.build();

		assertNotNull(actual);
		assertTrue(actual.containsKey(new BasicUser(1, "bob")));
		assertTrue(actual.containsKey(new BasicUser(2, "sally")));
		assertTrue(actual.get(new BasicUser(1, "bob")).containsKey("likes"));
		assertTrue(actual.get(new BasicUser(2, "sally")).containsKey("likes"));
		assertEquals(expected, actual);
	}
}