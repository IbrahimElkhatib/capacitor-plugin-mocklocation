import Foundation

@objc public class MockLocation: NSObject {
    @objc public func check(_ value: String) -> String {
        print(value)
        return value
    }
}
