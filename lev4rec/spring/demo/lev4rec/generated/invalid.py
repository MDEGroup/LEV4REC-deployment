
def load_dataset():
from surprise import Dataset, Reader



	data = pd.read_csv(''/mypath'', sep=",")	
	return data
 

def set_preprocessing():
	from sklearn.feature_extraction.text import CountVectorizer
    preprocess = CountVectorizer(ngram_range=(1,1))    

	return preprocess
def algorithm_settings():

	is_user_based=False
	neighborhood=0
	cutoff=0
	sim_funct='cosine'
	sim_settings = {'name': sim_funct,
               'user_based': is_user_based  # compute  similarities between items
               }
	from surprise import KNNWithMeans
	algo = KNNWithMeans(k=neighborhood, sim_options=sim_settings)


	return algo
def run_cross_fold():


from surprise.model_selection import KFold
from collections import defaultdict
kf = KFold(n_splits=n_splits)
#algo = SVD()
		from sklearn.feature_extraction.text import CountVectorizer
	    preprocess = CountVectorizer(ngram_range=(1,1))    
	



	


	is_user_based=False
	


	neighborhood=0
	


	cutoff=0
	


	sim_funct='cosine'
	


	sim_settings = {'name': sim_funct,
	


               'user_based': is_user_based  # compute  similarities between items
	


               }
	


	from surprise import KNNWithMeans
	


	algo = KNNWithMeans(k=neighborhood, sim_options=sim_settings)
	



	




threshold = 3.5
k=10
for trainset, testset in kf.split(data):
    algo.fit(trainset)
    predictions = algo.test(testset)
    #precisions, recalls = precision_recall_at_k(predictions, k=5, threshold=4)

	precision= sum(prec for prec in precisions.values()) / len(precisions)
	recall =sum(rec for rec in recalls.values()) / len(recalls)
    # Precision and recall can then be averaged over all users
    print(precision)
    print(recall)





