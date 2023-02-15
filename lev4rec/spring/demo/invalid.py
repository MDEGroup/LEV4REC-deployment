



def load_dataset():



def set_preprocessing():
	dataset.drop_duplicates(inPlace=True)
	X = dataset.iloc[:,:-1].values
	y = dataset.iloc[:, -1].values		
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



