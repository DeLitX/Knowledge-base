fun main(args: Array<String>) {
    val knowledgeBase = KnowledgeBase()
    while (true) {
        val parameter = knowledgeBase.getNextParameter()
        if (parameter == null) {
            println("There are no more parameters")
            println()
            println("Restarting...")
            println()
            knowledgeBase.restart()
            continue
        }
        println("Is your character ${parameter.title}?")
        println("1 - Yes, 2 - No")
        var userAnswer = readln().trim()
        while (userAnswer !in listOf("1", "2")) {
            println("Please enter correct answer")
            userAnswer = readln().trim()
        }
        val answer = when (userAnswer) {
            "1" -> Answer.Yes
            else -> Answer.No
        }
        val entity = knowledgeBase.addAnswer(parameter, answer)
        if (entity != null) {
            println("Your character is ${entity.name}")
            println()
            println("Restarting...")
            println()
            knowledgeBase.restart()
        }
    }
}
