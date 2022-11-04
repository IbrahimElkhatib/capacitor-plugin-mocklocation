import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(MockLocationPlugin)
public class MockLocationPlugin: CAPPlugin {
    private let implementation = MockLocation()

    @objc func check(_ call: CAPPluginCall) {
//        let value = call.getArray("whiteList") ?? []
        call.resolve([
            "mockDetected": false
        ])
    }
}
