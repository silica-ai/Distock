# import pandas as pd
# import yfinance as yf
# from yahoofinancials import YahooFinancials
# import matplotlib.pyplot as plt
# import os
#
# ticker = "BTC-USD"
#
# newtime = yf.download(ticker, period="1mo", interval="1d")
# newtime['Adj Close'].plot()
# plt.xlabel("Date")
# plt.ylabel("Adjusted")
# plt.title("Microsoft Price data")
# plt.style.use('dark_background')
# plt.show()


import plotly.graph_objects as go
import yfinance as yf
import os
import sys
from yahoofinancials import YahooFinancials
import pandas as pd
from datetime import datetime

ticker = sys.argv[1]
period = sys.argv[2]
interval = sys.argv[3]
title = "{0} {1} {2} Chart".format(ticker, period, interval)

newtime = yf.download(ticker, period=period, interval=interval)

dates = []
currentDirectory = os.getcwd()
path = currentDirectory + "/src/images/fig1.jpg"
for i, j in newtime.iterrows():
    dates.append(i)

fig = go.Figure(data=[go.Candlestick(x=dates,
                open=newtime['Open'],
                high=newtime['High'],
                low=newtime['Low'],
                close=newtime['Close'])])

fig.update_layout(
    title=title,
    yaxis_title='Price',
    xaxis_rangeslider_visible=False)

fig.write_image(path)
