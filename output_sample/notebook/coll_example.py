import nbformat as nbf

nb = nbf.v4.new_notebook()

text = """\
# Lowcode model generator
Generated recommneder system from model."""

code = """\
import pandas as pd
import warnings
warnings.filterwarnings("ignore")
##TODO Generating dataset

from surprise import Dataset
data = Dataset.load_builtin('ml-100k')






is_user_based=True
neighborhood=40
cutoff=5

sim_funct='cosine'

sim_settings = {'name': sim_funct,
               'user_based': is_user_based  # compute  similarities between items
               }
from surprise import KNNWithMeans
algo = KNNWithMeans(k=neighborhood, sim_options=sim_settings)





n_splits=10


from surprise.model_selection import KFold
from collections import defaultdict
kf = KFold(n_splits=n_splits)
#algo = SVD()
			NORMALIZATION






is_user_based=True
neighborhood=40
cutoff=5

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
	#top_n = get_top_n(predictions,n=cutoff)

#for uid, user_ratings in top_n.items():
#	print(uid, [ iid for (iid, _) in user_ratings])






"""
nb["cells"] = [nbf.v4.new_markdown_cell(text),
               nbf.v4.new_code_cell(code)]

nbf.write(nb, 'Generated Model.ipynb')
