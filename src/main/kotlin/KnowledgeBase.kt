class KnowledgeBase() {

    companion object {
        val initialEntitiesList = listOf(
            Entity("Will Smith", setOf(Parameter.Famous, Parameter.Rich, Parameter.Actor, Parameter.Bald)),
            Entity("Jim Carey", setOf(Parameter.Famous, Parameter.Rich, Parameter.Actor, Parameter.Old)),
            Entity("Bill Gates", setOf(Parameter.Famous, Parameter.Rich, Parameter.Smart, Parameter.Technical)),
            Entity(
                "Steve Jobs",
                setOf(
                    Parameter.Famous,
                    Parameter.Rich,
                    Parameter.Smart,
                    Parameter.Manager,
                    Parameter.Bald,
                    Parameter.Old,
                    Parameter.Dead
                )
            ),
            Entity("Mozart", setOf(Parameter.Famous, Parameter.Musician, Parameter.Dead)),
            Entity("Steve Wozniak", setOf(Parameter.Smart, Parameter.Technical, Parameter.Old)),
            Entity("Linus Torvalds", setOf(Parameter.Famous, Parameter.Smart, Parameter.Technical)),
        )
    }

    private var currentEntities: List<Entity> = initialEntitiesList
    private val parametersLeft: MutableList<Parameter> = Parameter.values().toMutableList()

    fun getNextParameter(): Parameter? {
        val parametersUsage = mutableMapOf<Parameter, MutableList<Entity>>()
        for (entity in currentEntities) {
            for (parameter in entity.parameters) {
                parametersUsage.getOrPut(parameter) { mutableListOf() }.add(entity)
            }
        }
        val list = parametersUsage.toList().filter { it.first in parametersLeft }.sortedBy { it.second.size }
        return list.lastOrNull()?.first
    }

    fun addAnswer(parameter: Parameter, answer: Answer): Entity? {
        parametersLeft.remove(parameter)
        return when (answer) {
            Answer.Yes -> {
                currentEntities = currentEntities.filter { it.parameters.contains(parameter) }
                if (currentEntities.size == 1) {
                    currentEntities.first()
                } else {
                    null
                }
            }

            Answer.No -> {
                currentEntities = currentEntities.filter { !it.parameters.contains(parameter) }
                if (currentEntities.size == 1) {
                    currentEntities.first()
                } else {
                    null
                }
            }
        }
    }

    fun restart() {
        currentEntities = initialEntitiesList
        parametersLeft.clear()
        parametersLeft.addAll(Parameter.values())
    }
}
