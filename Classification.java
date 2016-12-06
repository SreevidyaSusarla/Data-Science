import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.core.FastVector;
import weka.core.Instances;
 
public class WekaTest {
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
 
		return inputReader;
	}
 
	public static Evaluation classify(Classifier model,
			Instances trainingSet, Instances testingSet) throws Exception {
		Evaluation evaluation = new Evaluation(trainingSet);
 
		model.buildClassifier(trainingSet);
		evaluation.evaluateModel(model, testingSet);
 
		return evaluation;
	}
 
	public static double calculateAccuracy(FastVector predictions) {
		double correct = 0;
 
		for (int i = 0; i < predictions.size(); i++) {
			NominalPrediction np = (NominalPrediction) predictions.elementAt(i);
			if (np.predicted() == np.actual()) {
				correct++;
			}
		}
 
		return 100 * correct / predictions.size();
	}
 
	public static Instances[][] crossValidationSplit(Instances data, int numberOfFolds) {
		Instances[][] split = new Instances[2][numberOfFolds];
 
		for (int i = 0; i < numberOfFolds; i++) {
			split[0][i] = data.trainCV(numberOfFolds, i);
			split[1][i] = data.testCV(numberOfFolds, i);
		}
 
		return split;
	}
 
	public static void main(String[] args) throws Exception {
		//BufferedReader datafile = readDataFile("C:/Users/susar/Desktop/Teams.arff");
		BufferedReader datafile = readDataFile("C:/Users/susar/Desktop/DATA/hypothyroid.arff");
		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
 
		Instances[][] split = crossValidationSplit(data, 10);
		Instances[] trainingSplits = split[0];
		Instances[] testingSplits = split[1];
		Classifier[] models = { 
				new J48(), 
				new DecisionTable()
		};
 
		for (int j = 0; j < models.length; j++) {
 
			FastVector predictions = new FastVector();
			for (int i = 0; i < trainingSplits.length; i++) {
				Evaluation validation = classify(models[j], trainingSplits[i], testingSplits[i]);
 
				predictions.appendElements(validation.predictions());
			}
			double accuracy = calculateAccuracy(predictions);
			System.out.println("Accuracy of " + models[j].getClass().getSimpleName() + ": "
					+ String.format("%.2f%%", accuracy)
					+ "\n---------------------------------");
			Classifier cls = new J48();
			 Evaluation eval = new Evaluation(data);
			 Random rand = new Random(1);  // using seed = 1
			 int folds = 21;
			 eval.crossValidateModel(cls, data, folds, rand);
			 System.out.println(eval.toMatrixString());
			
		}
 
	}
}
