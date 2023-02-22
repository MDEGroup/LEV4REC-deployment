import pandas as pd		
def load_dataset():
	data=pd.read_csv(''/SocialNetwork.csv'')
	X = data.iloc[:,:-1].values
	y = data.iloc[:, -1].values
	return X,y



def set_preprocessing():
	from sklearn.feature_extraction.text import CountVectorizer
	preprocess = CountVectorizer(ngram_range=(1,1))    
	return preprocess
def algorithm_settings():
	from sklearn.neural_network import MLPClassifier	
	solver='ADAM'
	alpha=0.5
	random_state=1
	algo = MLPClassifier(solver=solver, alpha=alpha,random_state=random_state)
	return algo
def run_cross_fold():
	X,y = load_dataset()
	from sklearn.model_selection import  KFold
	n_splits=10
	kf = KFold(n_splits = n_splits)
	prec_all = 0	
	rec_all = 0
	f1_all = 0
	list_metrics=[]
	for  train, test in kf.split(X):
		X_split, X_test, y_split, y_test = X[train],X[test], y[train], y[test]	
		sc=set_preprocessing()
		list_train=[]
		list_test =[]
		for x in X_split.tolist():
			list_train.append(str(x))
		for t in X_test.tolist():
			list_test.append(str(t))
		X_train=sc.fit_transform(list_train)
		X_test=sc.transform(list_test)
		clf= algorithm_settings()
		clf.fit(X_train, y_split)
		y_pred= clf.predict(X_test)					
	prec_all=(prec_all /n_splits)
	rec_all=(rec_all /n_splits)
	f1_all=(f1_all /n_splits)

	list_metrics.append(prec_all)
	list_metrics.append(rec_all)
	list_metrics.append(f1_all)

	return list_metrics
    



if __name__=='main':
	run_cross_fold()
