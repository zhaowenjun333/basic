
def base64decode(s:str) -> str:
    base64_str = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'
    base64_dict = {y: bin(x)[2:].zfill(6) for x, y in enumerate(base64_str)}

    def base64bin(b:str) -> str:
        return ''.join([base64_dict[i] for i in b])
    if s:
        if s.endswith('=='):
            s = base64bin(s.strip('='))[:-4]
        elif s.endswith('='):
            s = base64bin(s.strip('='))[:-2]
        else:
            s = base64bin(s)
    # bin_str -> by
    by = int(s, 2).to_bytes(len(s)//8, 'big')
    return by.decode()
a = 'TWFu'
b = 'YWJjZA=='
c = 'aGVsbG8gd29ybGQ='
e = 'aGVsbG8gd29ybGQg5Lit5paH5a2X56ym'
print(base64decode(b))