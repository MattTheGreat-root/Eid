package todo.serializers;

import db.Entity;
import db.Serializer;
import todo.entity.Step;

public class StepSerializer implements Serializer {

    private static final String FILE_PATH = "steps.txt";

    @Override
    public String serialize(Entity e) {
        if (e instanceof Step){
            Step step = (Step) e;
            return Step.STEP_ENTITY_CODE + "|" + step.id + "|" + step.title + "|" + step.status + "|" + step.taskRef;
        }
        throw new IllegalArgumentException("Cannot serialize non-Step entity.");
    }

    @Override
    public Entity deserialize(String s) {
        String[] parts = s.split("\\|");
        Step step = new Step(parts[2], Step.Status.valueOf(parts[3]), Integer.parseInt(parts[4]));
        step.id = Integer.parseInt(parts[1]);
        return step;
    }
}
