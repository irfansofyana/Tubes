import math 
def translate3D(self, x, y, z):
	for i in self.arrPoint:
		i.x += x
		i.y += y
		i.z += z
	return self

def dilate3D(self, k):
	for i in self.arrPoint:
		i.x *= k
		i.y *= k
		i.z *= k
	return self

def reflect3D(self, x, y, z):
	for i in self.arrPoint:
		tmp = i.x - x
		i.x -= 2*tmp
		tmp = i.y - y
		i.y -= 2*tmp
		tmp = i.z - z
		i.z -= 2*tmp
	return self

def shear3D(self, a, b, c): 
	if (a=='x'):
		for i in self.arrPoint:
			i.x += b * i.y + c * i.z
	elif (a=='y'):
		for i in self.arrPoint:
			i.y += b * i.x + c * i.z
	elif (a == 'z'):
		for i in self.arrPoint:
			i.z += b * i.x + c * i.y
	return self


def rotate3D(self,a, deg):
	deg = (2*deg*math.acos(-1))/360.0
	cos = math.cos(deg)
	sin = math.sin(deg)
	if (a == 'x'):
		for i in self.arrPoint:
			tmp1 = i.y
			tmp2 = i.z
			i.y = tmp1 * cos - tmp2 * sin
			i.z = tmp1 * sin + tmp2 * cos
	elif (a == 'y'):
		for i in self.arrPoint:
			tmp1 = i.x
			tmp2 = i.z
			i.x = tmp1 * cos + tmp2 * sin
			i.z = -sin * tmp1 + tmp2 * cos 
	elif (a == 'z'):
		for i in self.arrPoint:
			tmp1 = i.x
			tmp2 = i.y
			i.x = tmp1 * cos - tmp2 * sin
			i.y = tmp1 * sin + tmp2 * cos
	return self

def stretch3D(self, param, k): 
	if (param == 'x'):
		for i in self.arrPoint:
			i.x *= k
	elif (param == 'y'):
		for i in self.arrPoint:
			i.y *= k
	elif (param == 'z'):
		for i in self.arrPoint:
			i.z *= k
	return self

def custom3D (self, a, b, c, d, e, f, g, h, j):
	for i in self.arrPoint:
		tx = (i.x) * a + (i.y) * d + (i.z) * g
		ty = (i.x) * b + (i.y) * e + (i.z) * h
		tz = (i.x) * c + (i.y) * f + (i.z) * j
		i.x = tx
		i.y = ty
		i.z = tz
	return self

def reset3D(self, point):
	for i, j in zip(self.arrPoint, point.arrPoint):
		i.x = j.x
		i.y = j.y
		i.z = j.z
	return self