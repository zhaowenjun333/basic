
import copy
lst0 = [1, [2, 3, 4], 5]
lst5 = copy.deepcopy(lst0)
lst5[1][1] = 20
print(lst5 == lst0)           # copy 类的方法deepcopy(list) 拷贝的是   数值,他会在内存中 新建一块用来装引用
