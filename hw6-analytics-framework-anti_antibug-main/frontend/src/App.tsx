import Handlebars from 'handlebars'
import { Component, ReactNode } from 'react'
import './App.css'
import ReactECharts from 'echarts-for-react'

var oldHref = 'http://localhost:3000'

interface DataParam {
  text: String
}

interface MapButton {
  text: String
  link: String
}

interface Plugin {
  name: String
  link: String
}

interface State {
  registeredDataPlugins: Plugin[]
  registeredVisualPlugins: Plugin[]
  dataPlugin: Plugin
  visualPlugin: Plugin
  dataParams: DataParam[]
  mapButtons: MapButton[]
  title: String
  url: String
  contentType: String
  mediaType: String
  mapText: String
  map: any
  instruction: String
  template: HandlebarsTemplateDelegate<any>
}

interface Props {
  name: string
}

class App extends Component<Props, State> {
  constructor (props: Props) {
    super(props)
    this.state = {
      registeredDataPlugins: [
        { name: 'Load Data Plugin', link: '/start' }
      ],
      registeredVisualPlugins: [
        { name: 'Load Visual Plugin', link: '/start' }
      ],
      dataPlugin: { name: '', link: '' },
      visualPlugin: { name: '', link: '' },
      dataParams: [{ text: 'Params To be initialed' }],
      mapButtons: [{ text: 'Buttons To be initialed', link: '' }],
      title: 'New Sentiment Analysis',
      url: '',
      contentType: 'No Type',
      mediaType: 'No Type',
      mapText: 'Please select your favorite data plugin & visual plugin to do the sentimental analysis.',
      map: {},
      instruction: "Please wait for the processing...",
      // {"radar":{"indicator":[{"name":"very negative","max":5},{"name":"negative","max":5},{"name":"neutral","max":5},{"name":"positive","max":5},{"name":"very positive","max":5}]},"series":[{"type":"radar","name":"satisfaction level","data":[{"value":[1,2,3,4,5]}]}]}
      // map: {"xAxis":[{"type":"category","name":"X-Axis","data":[]}],"yAxis":[{"type":"category","name":"Y-Axis"}],"series":[{"type":"scatter","data":[]}]}
      // ,
      // ,
      // {"xAxis":[{"type":"category","name":"time","data":[1.0,4.0,7.0,10.0,13.0,16.0,19.0,22.0,25.0,28.0,31.0,34.0,37.0,40.0,43.0],"boundaryGap":false}],"yAxis":[{"type":"value","name":"avrage rate"}],"tooltip":{"trigger":"item"},"visualMap":{"min":0,"max":4},"series":[{"type":"line","data":[2.25,2.5,2.0,1.5,1.0,1.6666666666666667,3.3333333333333335,1.6666666666666667,1.0,1.6666666666666667,3.5,1.5,2.0,4.0,2.0],"areaStyle":{}}]},

      // {"xAxis":[{"type":"category","name":"time","data":[1.0,4.0,7.0,10.0,13.0],"boundaryGap":false}],"yAxis":[{"type":"value","name":"avrage rate"}],"tooltip":{"trigger":"item"},"series":[{"type":"line","data":[2.25,2.5,2.0,1.5,1.0],"areaStyle":{}}]},

      // {"xAxis":[{"type":"category","name":"time","data":[1.0,4.0,7.0,10.0,13.0]}],"yAxis":[{"type":"value","name":"number of rate"}],"tooltip":{"trigger":"item"},"legend":{},"series":[{"type":"bar","name":"negative","data":[1.0,1.0,0.0,1.0,1.0]},{"type":"bar","name":"neutral","data":[1.0,0.0,1.0,0.0,0.0]},{"type":"bar","name":"positive","data":[2.0,1.0,0.0,1.0,0.0]}]},

      template: this.loadTemplate()
    }
  }

  loadTemplate (): HandlebarsTemplateDelegate<any> {
    const src = document.getElementById('handlebars')
    return Handlebars.compile(src?.innerHTML, {})
  }

  convertToDataPlugins (p: any): Plugin[] {
    const newDataPlugins: Plugin[] = []
    for (var i = 0; i < p.registeredDataPlugins.length; i++) {
      var plug: Plugin = {
        name: p.registeredDataPlugins[i].pluginName,
        link: p.registeredDataPlugins[i].link
      }
      newDataPlugins.push(plug)
    }

    return newDataPlugins
  }

  convertToVisualPlugins (p: any): Plugin[] {
    const newVisualPlugins: Plugin[] = []
    for (var i = 0; i < p.registeredVisualPlugins.length; i++) {
      var plug: Plugin = {
        name: p.registeredVisualPlugins[i].pluginName,
        link: p.registeredVisualPlugins[i].link
      }
      newVisualPlugins.push(plug)
    }

    return newVisualPlugins
  }

  convertToDataPlugin (p: any): Plugin {
    const newDataPlugin: Plugin = {
      name: p.dataPlugin.pluginName,
      link: p.dataPlugin.link
    }
    return newDataPlugin
  }

  convertToVisualPlugin (p: any): Plugin {
    const newVisualPlugin: Plugin = {
      name: p.visualPlugin.pluginName,
      link: p.visualPlugin.link
    }
    return newVisualPlugin
  }

  convertToParam (p: any): DataParam[] {
    const newParams: DataParam[] = []
    for (var i = 0; i < p.dataParams.length; i++) {
      var param: DataParam = {
        text: p.dataParams[i].text
      }
      newParams.push(param)
    }

    return newParams
  }

  convertToButton (p: any): MapButton[] {
    const newButtons: MapButton[] = []
    for (var i = 0; i < p.mapButtons.length; i++) {
      var c: MapButton = {
        text: p.mapButtons[i].text,
        link: p.mapButtons[i].link
      }
      newButtons.push(c)
    }
    return newButtons
  }

  convertToTitle (p: any): string {
    const newTitle: string = p.title
    return newTitle
  }

  convertToURL (p: any): string {
    const newTitle: string = p.url
    return newTitle
  }

  convertToContentType (p: any): string {
    const newTitle: string = p.contentType
    return newTitle
  }

  convertToMediaType (p: any): string {
    const newTitle: string = p.mediaType
    return newTitle
  }

  convertToMapText (p: any): string {
    const newTitle: string = p.mapText
    return newTitle
  }

  convertToMap (p: any): any {
    let newMap: any
    if (p.map === '') {
      newMap = { xAxis: [{ type: 'category', name: 'number of comments', data: [1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 18.0, 19.0, 20.0, 21.0, 22.0, 23.0, 24.0, 25.0, 26.0, 27.0, 28.0, 29.0, 30.0, 31.0, 32.0, 33.0, 34.0, 35.0, 36.0, 37.0] }], yAxis: [{ type: 'value', name: 'average rate among all time' }], series: [{ type: 'scatter', data: [0.0, 2.0, 2.0, 2.25, 2.0, 2.3333333333333335, 2.2857142857142856, 2.375, 2.111111111111111, 2.0, 2.0, 1.9166666666666667, 1.9230769230769231, 2.0714285714285716, 2.066666666666667, 2.1875, 2.0588235294117645, 2.0555555555555554, 2.1052631578947367, 2.0, 1.9523809523809523, 1.9545454545454546, 2.0, 1.9166666666666667, 1.92, 1.9615384615384615, 2.037037037037037, 2.0, 2.0, 1.9333333333333333, 2.0, 2.0625, 2.121212121212121, 2.1176470588235294, 2.085714285714286, 2.111111111111111, 2.081081081081081] }] }
    } else {
      newMap = p.map
    }
    return newMap
  }

  stateSet (p: any): void {
    const newDataPlugins: Plugin[] = this.convertToDataPlugins(p)
    const newVisualPlugins: Plugin[] = this.convertToVisualPlugins(p)
    const newDataPlugin: Plugin = this.convertToDataPlugin(p)
    const newVisualPlugin: Plugin = this.convertToVisualPlugin(p)
    const newDataParams: DataParam[] = this.convertToParam(p)
    const newMapButtons: MapButton[] = this.convertToButton(p)
    const newTitle: string = this.convertToTitle(p)
    const newURL: string = this.convertToURL(p)
    const newContentType: string = this.convertToContentType(p)
    const newMediaType: string = this.convertToMediaType(p)
    const newMapText: string = this.convertToMapText(p)
    const newMap: any = this.convertToMap(p)
    const newInstruction: string = p["instruction"]
    this.setState({
      registeredDataPlugins: newDataPlugins,
      registeredVisualPlugins: newVisualPlugins,
      dataPlugin: newDataPlugin,
      visualPlugin: newVisualPlugin,
      dataParams: newDataParams,
      mapButtons: newMapButtons,
      title: newTitle,
      url: newURL,
      contentType: newContentType,
      mediaType: newMediaType,
      mapText: newMapText,
      map: newMap,
      instruction: newInstruction
    })
  }

  async start (): Promise<void> {
    const href = 'start'
    const response = await fetch(href)

    const json = await response.json()
    // console.log(json);
    // const newDataPlugins: Plugin[] = this.convertToDataPlugins(json)
    // const newVisualPlugins: Plugin[] = this.convertToVisualPlugins(json)
    // this.setState({ registeredDataPlugins: newDataPlugins, registeredVisualPlugins: newVisualPlugins })
    this.stateSet(json)
  }

  async chooseDataPlugin (url: String): Promise<void> {
    const href = 'chooseDataPlugin?' + url.split('?')[1]
    const response = await fetch(href)
    const json = await response.json()

    // const newDataParams: DataParam[] = this.convertToParam(json)
    // const newDataPlugin: Plugin = this.convertToDataPlugin(json)

    // this.setState({ dataParams: newDataParams, dataPlugin: newDataPlugin, mapText: 'Please input params to collect data.' })
    this.stateSet(json)
  }

  async collectData (url: String): Promise<void> {
    const href = 'collectData?' + url.split('?')[1]
    const response = await fetch(href)
    const json = await response.json()

    // const newTitle: string = json["title"]
    // const newURL: string = json["url"]
    // const newContentType: string = json["contentType"]
    // const newMediaType: string = json["mediaType"]

    // this.setState({
    //   title: newTitle,
    //   url: newURL,
    //   contentType: newContentType,
    //   mediaType: newMediaType,
    //   mapText: 'Please select visual plugin to show sentimental results.'
    // })
    this.stateSet(json)
  }

  async startAnalysis (): Promise<void> {
    const href = 'startAnalysis'
    const response = await fetch(href)
    const json = await response.json()

    this.stateSet(json)
  }

  async chooseVisualPlugin (url: String): Promise<void> {
    const href = 'chooseVisualPlugin?' + url.split('?')[1]
    const response = await fetch(href)
    const json = await response.json()

    // const newMapButtons: MapButton[] = this.convertToButton(json)
    // const newVisualPlugin: Plugin = this.convertToVisualPlugin(json)

    // this.setState({ visualPlugin: newVisualPlugin, mapButtons: newMapButtons, mapText: 'Please select maps to show.' })
    this.stateSet(json)
  }

  async presentMap (url: String): Promise<void> {
    const href = 'presentMap?' + url.split('?')[1]
    const response = await fetch(href)
    const json = await response.json()

    // const newMap: any = json["map"]
    // console.log(newMap)
    // const newMapButtons: MapButton[] = this.convertToButton(json)
    // const newVisualPlugin: Plugin = this.convertToVisualPlugin(json)

    // const newMapText: string = json["mapText"]
    // this.setState({ map: newMap, mapText: newMapText, visualPlugin: newVisualPlugin, mapButtons: newMapButtons})
    this.stateSet(json)
  }

  async switch (): Promise<any> {
    if (
      window.location.href.split('?')[0] === 'http://localhost:3000/chooseDataPlugin' &&
      oldHref !== window.location.href
    ) {
      oldHref = window.location.href
      await this.chooseDataPlugin(window.location.href)
      // oldHref = window.location.href
    } else if (
      window.location.href.split('?')[0] === 'http://localhost:3000/chooseVisualPlugin' &&
        oldHref !== window.location.href
    ) {
      oldHref = window.location.href
      await this.chooseVisualPlugin(window.location.href)
      // oldHref = window.location.href
    } else if (
      window.location.href.split('?')[0] === 'http://localhost:3000/collectData' &&
      oldHref !== window.location.href
    ) {
      oldHref = window.location.href
      await this.collectData(window.location.href)
      // oldHref = window.location.href
    } else if (
      window.location.href.split('?')[0] === 'http://localhost:3000/presentMap' &&
      oldHref !== window.location.href
    ) {
      oldHref = window.location.href
      await this.presentMap(window.location.href)
      // oldHref = window.location.href
    } else if (
      window.location.href.split('?')[0] === 'http://localhost:3000/startAnalysis' &&
      oldHref !== window.location.href
    ) {
      oldHref = window.location.href
      await this.startAnalysis()
      // oldHref = window.location.href
    } else if (
      (window.location.href === 'http://localhost:3000/' ||
      window.location.href === 'http://localhost:3000/start') &&
      oldHref !== window.location.href
    ) {
      oldHref = window.location.href
      await this.start()
      // oldHref = window.location.href
    }
  };

  render (): ReactNode {
    this.switch().finally(() => console.log('Request Done'))
    return (
      <><div className='App'>
        <div
          dangerouslySetInnerHTML={{
            __html: this.state.template({
              registeredDataPlugins: this.state.registeredDataPlugins,
              registeredVisualPlugins: this.state.registeredVisualPlugins,
              dataPlugin: this.state.dataPlugin,
              visualPlugin: this.state.visualPlugin,
              dataParams: this.state.dataParams,
              mapButtons: this.state.mapButtons,
              title: this.state.title,
              url: this.state.url,
              contentType: this.state.contentType,
              mediaType: this.state.mediaType,
              mapText: this.state.mapText,
              map: this.state.map,
              instruction: this.state.instruction,
            })
          }}
        />

      </div>
        <div className='chart'>
        <div id='map'>
            <h1>Result of Visualization</h1>
            <ReactECharts option={this.state.map} />
          </div>
      </div>
      </>
    )
  };
};

export default App
