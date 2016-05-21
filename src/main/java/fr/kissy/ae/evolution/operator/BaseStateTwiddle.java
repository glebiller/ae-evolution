package fr.kissy.ae.evolution.operator;

import com.google.common.collect.Lists;
import fr.kissy.ae.evolution.BaseState;
import fr.kissy.ae.evolution.action.Action;
import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Random;

/**
 * @author Guillaume Le Biller (<i>guillaume.lebiller@gmail.com</i>)
 * @version $Id$
 */
public class BaseStateTwiddle implements EvolutionaryOperator<BaseState> {
    private final int possibleActionsSize;
    private final List<Action> possibleActions;
    private final NumberGenerator<Probability> mutationProbability;

    public BaseStateTwiddle(List<Action> possibleActions, NumberGenerator<Probability> mutationProbability) {
        this.possibleActionsSize = possibleActions.size();
        this.possibleActions = possibleActions;
        this.mutationProbability = mutationProbability;
    }

    @Override
    public List<BaseState> apply(List<BaseState> selectedCandidates, Random rng) {
        List<BaseState> mutatedPopulation = Lists.newArrayListWithCapacity(selectedCandidates.size());
        for (BaseState s : selectedCandidates) {
            mutatedPopulation.add(mutate(s, rng));
        }
        return mutatedPopulation;
    }

    private BaseState mutate(BaseState baseState, Random rng) {
        final BaseState copy = baseState.copy();
        if (!mutationProbability.nextValue().nextEvent(rng)) {
            return copy;
        }

        final int actionsSize = copy.getActions().size();
        int randomPoint = (int) (Math.random() * actionsSize);
        if (randomPoint < actionsSize - 1) {
            Action swap = copy.getActions().get(randomPoint);
            copy.getActions().set(randomPoint, copy.getActions().get(randomPoint + 1));
            copy.getActions().set(randomPoint + 1, swap);
        }
        return copy;
    }
}

