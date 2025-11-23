Feature: CRM to Workload integration tests

  Scenario: Creating a training updates workload service
    Given workload-service is running
    When I create a training for trainer "trainer1"
    Then workload-service should have workload increased by 50
