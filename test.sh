# command to press control + c to stop all processes
(trap 'kill 0' SIGINT; (cd clientC4Program; mvn exec:java & mvn exec:java) & (cd serverC4Program; mvn exec:java && fg))

