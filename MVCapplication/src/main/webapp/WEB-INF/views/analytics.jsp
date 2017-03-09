<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class="row" >
    <div id="locked">
        Work in progress...
    </div>
    <br/>
    <div class="col-lg-2"></div>
    <div class="col-lg-8" style="filter:blur(10px)">
        <div class="panel panel-default">
            <div class="panel-heading">
                <i class="fa fa-bar-chart-o fa-fw"></i> Area Chart Example
                <div class="pull-right">
                    <div class="btn-group">
                        <button type="button" class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown">
                            Actions
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu pull-right" role="menu">
                            <li><a href="#">Action</a>
                            </li>
                            <li><a href="#">Another action</a>
                            </li>
                            <li><a href="#">Something else here</a>
                            </li>
                            <li class="divider"></li>
                            <li><a href="#">Separated link</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <div id="morris-area-chart" style="position: relative; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);">
                    <svg height="347" version="1.1" width="723" xmlns="http://www.w3.org/2000/svg"
                         xmlns:xlink="http://www.w3.org/1999/xlink" style="overflow: hidden; position: relative;">
                        <desc style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);">Created with Raphaël 2.2.0</desc>
                        <defs style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></defs>
                        <text x="50.5" y="313" text-anchor="end" font-family="sans-serif" font-size="12px" stroke="none"
                              fill="#888888"
                              style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0); text-anchor: end; font-family: sans-serif; font-size: 12px; font-weight: normal;"
                              font-weight="normal">
                            <tspan style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);" dy="4">0</tspan>
                        </text>
                        <path fill="none" stroke="#aaaaaa" d="M63,313H698" stroke-width="0.5"
                              style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></path>
                        <text x="50.5" y="241" text-anchor="end" font-family="sans-serif" font-size="12px" stroke="none"
                              fill="#888888"
                              style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0); text-anchor: end; font-family: sans-serif; font-size: 12px; font-weight: normal;"
                              font-weight="normal">
                            <tspan style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);" dy="4">7,500</tspan>
                        </text>
                        <path fill="none" stroke="#aaaaaa" d="M63,241H698" stroke-width="0.5"
                              style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></path>
                        <text x="50.5" y="169" text-anchor="end" font-family="sans-serif" font-size="12px" stroke="none"
                              fill="#888888"
                              style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0); text-anchor: end; font-family: sans-serif; font-size: 12px; font-weight: normal;"
                              font-weight="normal">
                            <tspan style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);" dy="4">15,000</tspan>
                        </text>
                        <path fill="none" stroke="#aaaaaa" d="M63,169H698" stroke-width="0.5"
                              style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></path>
                        <text x="50.5" y="97.00000000000003" text-anchor="end" font-family="sans-serif" font-size="12px"
                              stroke="none" fill="#888888"
                              style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0); text-anchor: end; font-family: sans-serif; font-size: 12px; font-weight: normal;"
                              font-weight="normal">
                            <tspan style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);" dy="4.000000000000028">
                                22,500
                            </tspan>
                        </text>
                        <path fill="none" stroke="#aaaaaa" d="M63,97.00000000000003H698" stroke-width="0.5"
                              style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></path>
                        <text x="50.5" y="25" text-anchor="end" font-family="sans-serif" font-size="12px" stroke="none"
                              fill="#888888"
                              style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0); text-anchor: end; font-family: sans-serif; font-size: 12px; font-weight: normal;"
                              font-weight="normal">
                            <tspan style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);" dy="4">30,000</tspan>
                        </text>
                        <path fill="none" stroke="#aaaaaa" d="M63,25H698" stroke-width="0.5"
                              style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></path>
                        <text x="580.7479621284998" y="325.5" text-anchor="middle" font-family="sans-serif"
                              font-size="12px" stroke="none" fill="#888888"
                              style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0); text-anchor: middle; font-family: sans-serif; font-size: 12px; font-weight: normal;"
                              font-weight="normal" transform="matrix(1,0,0,1,0,7)">
                            <tspan style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);" dy="4">2012</tspan>
                        </text>
                        <text x="299.11158928661837" y="325.5" text-anchor="middle" font-family="sans-serif"
                              font-size="12px" stroke="none" fill="#888888"
                              style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0); text-anchor: middle; font-family: sans-serif; font-size: 12px; font-weight: normal;"
                              font-weight="normal" transform="matrix(1,0,0,1,0,7)">
                            <tspan style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);" dy="4">2011</tspan>
                        </text>
                        <path fill="#7cb57c" stroke="none"
                              d="M63,261.9952C80.73891195382512,256.7152,116.21673586147537,245.75689404303512,133.9556478153005,240.8752C151.70259733684372,235.9912940430351,187.19649637993012,229.61560628272252,204.94344590147335,222.93280000000001C222.50553136550047,216.31960628272253,257.6297022935548,189.66448883774453,275.1917877575819,187.6912C292.55293402865675,185.74048883774455,327.2752265708066,205.80161428898558,344.63637284188144,207.23680000000002C362.37528479570653,208.70321428898558,397.85310870335684,198.32102124575314,415.59202065718193,199.29760000000002C433.33897017872516,200.27462124575314,468.8328692218115,232.38668900523558,486.57981874335474,215.0512C504.1419042073819,197.8962890052356,539.2660751354362,69.70431494621197,556.8281605994633,61.33600000000001C574.3822084957724,52.97151494621197,609.4903042883905,135.84980951947165,627.0443521846995,148.12C644.7832641385246,160.51940951947165,680.2610880461749,157.04080000000002,698,160.01440000000002L698,313L63,313Z"
                              fill-opacity="1"
                              style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0); fill-opacity: 1;"></path>
                        <path fill="none" stroke="#4da74d"
                              d="M63,261.9952C80.73891195382512,256.7152,116.21673586147537,245.75689404303512,133.9556478153005,240.8752C151.70259733684372,235.9912940430351,187.19649637993012,229.61560628272252,204.94344590147335,222.93280000000001C222.50553136550047,216.31960628272253,257.6297022935548,189.66448883774453,275.1917877575819,187.6912C292.55293402865675,185.74048883774455,327.2752265708066,205.80161428898558,344.63637284188144,207.23680000000002C362.37528479570653,208.70321428898558,397.85310870335684,198.32102124575314,415.59202065718193,199.29760000000002C433.33897017872516,200.27462124575314,468.8328692218115,232.38668900523558,486.57981874335474,215.0512C504.1419042073819,197.8962890052356,539.2660751354362,69.70431494621197,556.8281605994633,61.33600000000001C574.3822084957724,52.97151494621197,609.4903042883905,135.84980951947165,627.0443521846995,148.12C644.7832641385246,160.51940951947165,680.2610880461749,157.04080000000002,698,160.01440000000002"
                              stroke-width="3" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></path>
                        <circle cx="63" cy="261.9952" r="2" fill="#4da74d" stroke="#ffffff" stroke-width="1"
                                style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="133.9556478153005" cy="240.8752" r="2" fill="#4da74d" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="204.94344590147335" cy="222.93280000000001" r="2" fill="#4da74d" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="275.1917877575819" cy="187.6912" r="2" fill="#4da74d" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="344.63637284188144" cy="207.23680000000002" r="2" fill="#4da74d" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="415.59202065718193" cy="199.29760000000002" r="2" fill="#4da74d" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="486.57981874335474" cy="215.0512" r="2" fill="#4da74d" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="556.8281605994633" cy="61.33600000000001" r="2" fill="#4da74d" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="627.0443521846995" cy="148.12" r="2" fill="#4da74d" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="698" cy="160.01440000000002" r="2" fill="#4da74d" stroke="#ffffff" stroke-width="1"
                                style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <path fill="#a8b4bd" stroke="none"
                              d="M63,287.4064C80.73891195382512,281.632,116.21673586147537,269.3656543601359,133.9556478153005,264.3088C151.70259733684372,259.24965436013593,187.19649637993012,249.70720000000003,204.94344590147335,246.94240000000002C222.50553136550047,244.20640000000003,257.6297022935548,244.52992497123134,275.1917877575819,242.30560000000003C292.55293402865675,240.10672497123133,327.2752265708066,232.33483379894662,344.63637284188144,229.2496C362.37528479570653,226.09723379894663,397.85310870335684,217.2244296262741,415.59202065718193,217.35520000000002C433.33897017872516,217.4860296262741,468.8328692218115,243.65799371727746,486.57981874335474,230.296C504.1419042073819,217.07319371727746,539.2660751354362,118.85619409475855,556.8281605994633,111.01600000000002C574.3822084957724,103.17939409475855,609.4903042883905,159.3581391027101,627.0443521846995,167.58880000000002C644.7832641385246,175.9061391027101,680.2610880461749,174.8032,698,177.208L698,313L63,313Z"
                              fill-opacity="1"
                              style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0); fill-opacity: 1;"></path>
                        <path fill="none" stroke="#7a92a3"
                              d="M63,287.4064C80.73891195382512,281.632,116.21673586147537,269.3656543601359,133.9556478153005,264.3088C151.70259733684372,259.24965436013593,187.19649637993012,249.70720000000003,204.94344590147335,246.94240000000002C222.50553136550047,244.20640000000003,257.6297022935548,244.52992497123134,275.1917877575819,242.30560000000003C292.55293402865675,240.10672497123133,327.2752265708066,232.33483379894662,344.63637284188144,229.2496C362.37528479570653,226.09723379894663,397.85310870335684,217.2244296262741,415.59202065718193,217.35520000000002C433.33897017872516,217.4860296262741,468.8328692218115,243.65799371727746,486.57981874335474,230.296C504.1419042073819,217.07319371727746,539.2660751354362,118.85619409475855,556.8281605994633,111.01600000000002C574.3822084957724,103.17939409475855,609.4903042883905,159.3581391027101,627.0443521846995,167.58880000000002C644.7832641385246,175.9061391027101,680.2610880461749,174.8032,698,177.208"
                              stroke-width="3" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></path>
                        <circle cx="63" cy="287.4064" r="2" fill="#7a92a3" stroke="#ffffff" stroke-width="1"
                                style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="133.9556478153005" cy="264.3088" r="2" fill="#7a92a3" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="204.94344590147335" cy="246.94240000000002" r="2" fill="#7a92a3" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="275.1917877575819" cy="242.30560000000003" r="2" fill="#7a92a3" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="344.63637284188144" cy="229.2496" r="2" fill="#7a92a3" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="415.59202065718193" cy="217.35520000000002" r="2" fill="#7a92a3" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="486.57981874335474" cy="230.296" r="2" fill="#7a92a3" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="556.8281605994633" cy="111.01600000000002" r="2" fill="#7a92a3" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="627.0443521846995" cy="167.58880000000002" r="2" fill="#7a92a3" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="698" cy="177.208" r="2" fill="#7a92a3" stroke="#ffffff" stroke-width="1"
                                style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <path fill="#2677b5" stroke="none"
                              d="M63,287.4064C80.73891195382512,287.1376,116.21673586147537,289.0257895356739,133.9556478153005,286.3312C151.70259733684372,283.6353895356739,187.19649637993012,267.03781361256546,204.94344590147335,265.8448C222.50553136550047,264.66421361256545,257.6297022935548,279.12750471806675,275.1917877575819,276.8368C292.55293402865675,274.57230471806673,327.2752265708066,249.8830226700252,344.63637284188144,247.62400000000002C362.37528479570653,245.31582267002523,397.85310870335684,256.1805408833522,415.59202065718193,258.568C433.33897017872516,260.9565408833522,468.8328692218115,278.0706764397906,486.57981874335474,266.728C504.1419042073819,255.50347643979057,539.2660751354362,175.34121144426643,556.8281605994633,168.2992C574.3822084957724,161.26041144426645,609.4903042883905,202.47734256433617,627.0443521846995,210.40480000000002C644.7832641385246,218.41574256433617,680.2610880461749,226.6408,698,232.0528L698,313L63,313Z"
                              fill-opacity="1"
                              style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0); fill-opacity: 1;"></path>
                        <path fill="none" stroke="#0b62a4"
                              d="M63,287.4064C80.73891195382512,287.1376,116.21673586147537,289.0257895356739,133.9556478153005,286.3312C151.70259733684372,283.6353895356739,187.19649637993012,267.03781361256546,204.94344590147335,265.8448C222.50553136550047,264.66421361256545,257.6297022935548,279.12750471806675,275.1917877575819,276.8368C292.55293402865675,274.57230471806673,327.2752265708066,249.8830226700252,344.63637284188144,247.62400000000002C362.37528479570653,245.31582267002523,397.85310870335684,256.1805408833522,415.59202065718193,258.568C433.33897017872516,260.9565408833522,468.8328692218115,278.0706764397906,486.57981874335474,266.728C504.1419042073819,255.50347643979057,539.2660751354362,175.34121144426643,556.8281605994633,168.2992C574.3822084957724,161.26041144426645,609.4903042883905,202.47734256433617,627.0443521846995,210.40480000000002C644.7832641385246,218.41574256433617,680.2610880461749,226.6408,698,232.0528"
                              stroke-width="3" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></path>
                        <circle cx="63" cy="287.4064" r="2" fill="#0b62a4" stroke="#ffffff" stroke-width="1"
                                style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="133.9556478153005" cy="286.3312" r="2" fill="#0b62a4" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="204.94344590147335" cy="265.8448" r="2" fill="#0b62a4" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="275.1917877575819" cy="276.8368" r="2" fill="#0b62a4" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="344.63637284188144" cy="247.62400000000002" r="2" fill="#0b62a4" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="415.59202065718193" cy="258.568" r="2" fill="#0b62a4" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="486.57981874335474" cy="266.728" r="2" fill="#0b62a4" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="556.8281605994633" cy="168.2992" r="2" fill="#0b62a4" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="627.0443521846995" cy="210.40480000000002" r="2" fill="#0b62a4" stroke="#ffffff"
                                stroke-width="1" style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                        <circle cx="698" cy="232.0528" r="2" fill="#0b62a4" stroke="#ffffff" stroke-width="1"
                                style="-webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></circle>
                    </svg>
                    <div class="morris-hover morris-default-style" style="left: 570.208px; top: 106px; display: none;">
                        <div class="morris-hover-row-label">2012 Q1</div>
                        <div class="morris-hover-point" style="color: #0b62a4">
                            iPhone:
                            10,687
                        </div>
                        <div class="morris-hover-point" style="color: #7A92A3">
                            iPad:
                            4,460
                        </div>
                        <div class="morris-hover-point" style="color: #4da74d">
                            iPod Touch:
                            2,028
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.panel-body -->
        </div>
        <!-- /.panel -->
    </div>
    <div class="col-lg-2"></div>
</div>

