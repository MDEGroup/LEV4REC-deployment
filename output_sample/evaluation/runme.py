import pandas as pd
##TODO Generating dataset

from surprise import Dataset

if __name__ == '__main__':
    data = Dataset.load_builtin('ml-100k', False)




    n_splits=10


    from surprise.model_selection import KFold
    from collections import defaultdict
    kf = KFold(n_splits=n_splits)
    #algo = SVD()




    is_user_based=False
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

        user_est_true = defaultdict(list)
        for uid, _, true_r, est, _ in predictions:
            user_est_true[uid].append((est, true_r))

        precisions = dict()
        recalls = dict()
        for uid, user_ratings in user_est_true.items():
            n_rel = sum((true_r >= threshold) for (_, true_r) in user_ratings)

            # Number of recommended items in top k
            n_rec_k = sum((est >= threshold) for (est, _) in user_ratings[:k])
            n_rel_and_rec_k = sum(((true_r >= threshold) and (est >= threshold))
                                for (est, true_r) in user_ratings[:k])
            precisions[uid] = n_rel_and_rec_k / n_rec_k if n_rec_k != 0 else 0


            recalls[uid] = n_rel_and_rec_k / n_rel if n_rel != 0 else 0





        precision= sum(prec for prec in precisions.values()) / len(precisions)
        recall =sum(rec for rec in recalls.values()) / len(recalls)
        # Precision and recall can then be averaged over all users
        print(precision)
        print(recall)


        f1_measure=(2*precision* recall) / (recall + precision)
        print(f1_measure)