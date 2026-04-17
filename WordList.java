import java.util.Random;

public class WordList {
    public static String[] easyWords = {
            "cat", "dog", "sun", "ball", "tree", "book", "fish", "milk", "star", "rain",
            "apple", "chair", "table", "phone", "bread", "water", "shirt", "plant", "clock", "light",
            "pen", "cup", "hat", "bag", "shoe", "toy", "box", "door", "bed", "map",
            "leaf", "road", "car", "bus", "train", "ship", "bird", "egg", "cake", "salt",
            "rice", "tea", "soap", "ring", "coin", "key", "lock", "wall", "roof", "floor",
            "glass", "plate", "spoon", "fork", "knife", "brush", "comb", "mirror", "towel", "bucket",
            "rope", "stick", "stone", "sand", "dust", "wind", "fire", "smoke", "cloud", "sky",
            "hill", "lake", "river", "sea", "wave", "shell", "wood", "paper", "ink", "chalk",
            "board", "desk", "class", "school", "teacher", "student", "friend", "game", "play", "run",
            "jump", "walk", "sit", "stand", "read", "write", "draw", "paint", "sing", "dance",
            "laugh", "smile", "happy", "sad", "angry", "tired", "sleep", "dream", "wake", "think",
            "learn", "teach", "count", "add", "minus", "zero", "one", "two", "three", "four",
            "five", "six", "seven", "eight", "nine", "ten", "red", "blue", "green", "black",
            "white", "pink", "brown", "gray", "gold", "silver", "big", "small", "long", "short"
    };
    public static String[] mediumWords = {
            "computer", "library", "teacher", "student", "battery", "holiday", "picture", "diamond",
            "football", "chocolate", "keyboard", "internet", "language", "building", "mountain",
            "elephant", "umbrella", "airplane", "hospital", "painting", "festival", "weather",
            "history", "science", "college", "village", "country", "cityscape", "backpack", "notebook",
            "calculator", "headphones", "microphone", "television", "refrigerator", "newspaper",
            "magazine", "calendar", "sandwich", "breakfast", "lunchbox", "dinner", "kitchen",
            "bedroom", "bathroom", "balcony", "corridor", "elevator", "staircase", "highway",
            "traffic", "vehicle", "bicycle", "motorcycle", "railway", "airport", "station",
            "journey", "adventure", "explorer", "treasure", "island", "desert", "forest", "jungle",
            "valley", "plateau", "volcano", "earthquake", "hurricane", "tornado", "tsunami",
            "oxygen", "nitrogen", "hydrogen", "carbon", "element", "molecule", "gravity",
            "energy", "motion", "velocity", "friction", "pressure", "density", "temperature",
            "electricity", "magnetism", "radiation", "frequency", "amplitude", "spectrum",
            "algorithm", "function", "variable", "constant", "operator", "compiler", "debugger",
            "software", "hardware", "database", "network", "protocol", "browser", "server",
            "client", "security", "encryption", "password", "username", "account", "profile",
            "message", "notification", "application", "download", "upload", "storage",
            "document", "presentation", "spreadsheet", "analysis", "project", "assignment",
            "deadline", "schedule", "meeting", "discussion", "conference", "seminar",
            "training", "practice", "exercise", "fitness", "health", "medicine", "treatment",
            "disease", "infection", "symptom", "diagnosis", "recovery"
    };
    public static String[] hardWords = {
            "architecture", "encyclopedia", "transformation", "communication", "psychology",
            "microbiology", "philosophy", "mathematics", "cryptography", "astronomy",
            "environmental", "biotechnology", "neuroscience", "thermodynamics",
            "international", "consciousness", "industrialization", "metamorphosis",
            "electromagnetism", "photosynthesis", "antidisestablishmentarianism",
            "hypercommunication", "multidisciplinary", "characterization", "misinterpretation",
            "overcomplication", "underestimation", "reconstruction", "decentralization",
            "miscommunication", "counterproductive", "hyperactivity", "subconsciousness",
            "unpredictability", "overachievement", "underdevelopment", "disorganization",
            "interdependency", "miscalculation", "counterintuitive", "hyperventilation",
            "substitution", "transcription", "interpretation", "misrepresentation",
            "counterargument", "overpopulation", "underperformance", "reclassification",
            "decomposition", "reconfiguration", "misjudgment", "counterbalance",
            "hyperinflation", "subterranean", "transcontinental", "intercontinental",
            "misalignment", "counterculture", "overconsumption", "underutilization",
            "reaffirmation", "deactivation", "misconception", "counterattack",
            "hyperextension", "suboptimal", "transmutation", "interrelation",
            "misdiagnosis", "countermeasure", "overconfidence", "underestimation",
            "reconstruction", "degradation", "misinterpretation", "counterexample",
            "hyperrealistic", "subdivision", "transformation", "interconnection",
            "misapplication", "counterproductive", "overcompensation", "underachievement",
            "reorganization", "decentralization", "miscommunication", "counterbalance",
            "hypercommunication", "subconscious", "transfiguration", "interdependence",
            "misrepresentation", "counterintelligence", "overgeneralization",
            "underrepresentation", "reclassification", "deconstruction",
            "misinterpretation", "counterculture", "hyperactivity", "substitution",
            "transcription", "intercontinental", "misjudgment", "counterattack",
            "overpopulation", "underperformance", "reconfiguration", "decomposition",
            "misalignment", "counterargument", "hyperinflation", "subterranean",
            "transcontinental", "interrelation", "misdiagnosis", "countermeasure",
            "overconfidence", "underutilization", "reaffirmation", "deactivation",
            "misconception", "counterexample", "hyperextension", "suboptimal",
            "transmutation", "interconnection", "misapplication", "counterproductive"
    };

    public static String getRandomWord(String difficulty) {
        Random rand = new Random();
        if (difficulty.equalsIgnoreCase("easy")) {
            return easyWords[rand.nextInt(easyWords.length)];
        } else if (difficulty.equalsIgnoreCase("medium")) {
            return mediumWords[rand.nextInt(mediumWords.length)];
        } else {
            return hardWords[rand.nextInt(hardWords.length)];
        }
    }
}
