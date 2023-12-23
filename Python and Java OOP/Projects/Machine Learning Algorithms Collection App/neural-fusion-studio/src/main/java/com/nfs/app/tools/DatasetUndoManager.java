package com.nfs.app.tools;

import java.util.ArrayList;
import java.util.List;
import weka.core.Instances;

// Command interface
interface Command {
    void execute(Instances dataset);

    void undo(Instances dataset);
}

// Generic ModifyDatasetCommand
class ModifyDatasetCommand implements Command {
    private final int instanceIndex;
    private final ModificationFunction modificationFunction;

    public interface ModificationFunction {
        void apply(Instances dataset, int instanceIndex);
    }

    public ModifyDatasetCommand(int instanceIndex, ModificationFunction modificationFunction) {
        this.instanceIndex = instanceIndex;
        this.modificationFunction = modificationFunction;
    }

    @Override
    public void execute(Instances dataset) {
        modificationFunction.apply(dataset, instanceIndex);
    }

    @Override
    public void undo(Instances dataset) {
        // Undo the modification
        modificationFunction.apply(dataset, instanceIndex);
    }
}

class DatasetCommandExecutor {
    private final List<Command> commandList = new ArrayList<>();
    private int currentIndex = -1;

    public void executeCommand(Command command, Instances dataset) {
        // Remove commands after the current index since we're branching from here
        commandList.subList(currentIndex + 1, commandList.size()).clear();

        // Execute the command and save it
        command.execute(dataset);
        commandList.add(command);
        currentIndex++;
    }

    public void undo(Instances dataset) {
        if (currentIndex >= 0) {
            // Undo the last command
            commandList.get(currentIndex).undo(dataset);
            currentIndex--;
        }
    }
}

// DatasetUndoManager with a more generic approach
public class DatasetUndoManager {
    private final Instances dataset;
    private final DatasetCommandExecutor commandExecutor;

    public DatasetUndoManager(Instances initialDataset) {
        this.dataset = new Instances(initialDataset);
        this.commandExecutor = new DatasetCommandExecutor();
    }

    public Instances getDataset() {
        return new Instances(dataset);
    }

    public void executeModifyDatasetCommand(int instanceIndex, ModifyDatasetCommand.ModificationFunction modificationFunction) {
        commandExecutor.executeCommand(new ModifyDatasetCommand(instanceIndex, modificationFunction), dataset);
    }

    public void undo() {
        commandExecutor.undo(dataset);
    }

    public void destroy() {
        // Destroy the dataset
    }
}
