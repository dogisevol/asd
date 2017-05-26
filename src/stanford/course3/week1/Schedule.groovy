package stanford.course3.week1

class Job implements Comparable {

    Job(int weight, int length) {
        this.weight = weight
        this.length = length
    }
    int weight
    int length

    double getPriority() {
        weight - length
    }

    @Override
    String toString() {
        return "${weight} : ${length}"
    }

    @Override
    int compareTo(Object o) {
        int diff = ((Job) o).priority - this.priority
        if (diff == 0) {
            return ((Job) o).weight - this.weight
        } else
            return diff
    }
}

class RatioJob extends Job {

    RatioJob(int weight, int length) {
        super(weight, length)
    }

    @Override
    double getPriority() {
        return weight / length
    }

    @Override
    int compareTo(Object o) {
        def d = ((Job) o).priority - this.priority
        if (d > 0) {
            return 1
        } else if (d < 0) {
            return -1
        } else {
            return 0
        }
    }
}

Scanner scanner = new Scanner(new File("jobs.txt"));
List<Job> jobs = []
List<RatioJob> ratioJobs = []
while (scanner.hasNext()) {
    def job = new Job(scanner.nextInt(), scanner.nextInt())
    jobs.add(job)
    ratioJobs.add(new RatioJob(job.weight, job.length))
}

long sum = 0
def prevLength = 0
jobs.sort().each { job ->
    sum = sum + job.weight * (prevLength + job.length)
    prevLength += job.length
}
println sum
sum = 0
prevLength = 0
ratioJobs.sort().each { job ->
    sum = sum + job.weight * (prevLength + job.length)
    prevLength += job.length
}

println sum