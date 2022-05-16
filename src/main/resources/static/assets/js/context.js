const content = document.querySelector('#main')
const body = document.querySelector('body')
const main = addClassList(document.createElement('main'), '', {
    width: '100vw',
    height: 'calc(100vh - 80px)',
    marginTop: '80px',
    overflowX: 'hidden'
})

if (!user) {
    document.querySelector("body").style.backgroundColor = "#f1f1f1"
    document.querySelector("body").style.height = "100vh"
    document.querySelector("body").innerHTML = `<div class="d-flex flex-column h-100 align-items-center justify-content-center">
    <div class="text-center">
        <div class="">
            <img src="/assets/icons/lock.png" alt="lock icon" width="150px">
        </div>
        <div class="mt-3">
            <p class="w-75 text-center mx-auto">
                This page secured. To see content of the page you must
                <a class="px-0 nav-link" style="display: unset"
                   href="/auth/login?return_url=${location.pathname}">login</a> to system.
            </p>
        </div>

    </div>

</div>`
    throw new Error("You are not allowed to access this page")
}

let navbarItems = [
    {url: '/dashboard/most-sold', path: '/dashboard', name: 'Dashboard'},
    {url: '/users/user', path: '/users', name: 'Users'},
    {url: '/input/all', path: '/input', name: 'Input'},
    {url: '/output/all', path: '/output', name: 'Output'},
    {url: '/data/category', path: '/data', name: 'Data'},
]
let leftNavbarItems = {
    'Dashboard': [
        {url: '/dashboard/most-sold', name: 'Most sold'},
        {url: '/dashboard/notifications', name: 'Notifications'}
    ],
    'Input': [
        {url: '/input/all', name: 'Input list'},
        {url: '/input/addInput', name: 'Input add'}
    ],
    'Output': [
        {url: '/output/all', name: 'Output list'},
        {url: '/output/addOutput', name: 'Output add'}
    ],
    'Data': [
        {url: '/data/category', name: 'Category'},
        {url: '/data/product', name: 'Product'},
        {url: '/data/currency', name: 'Currency'},
        {url: '/data/warehouse', name: 'Warehouse'},
        {url: '/data/measurement', name: 'Measurement'}
    ],
    'Users': [
        {url: '/users/user', name: 'User'},
        {url: '/users/client', name: 'Client'},
        {url: '/users/supplier', name: 'Supplier'},
    ]
}


function addClassList(element, classList, style = {}) {
    let classes = classList.split(' ')
    for (let classH of classes) {
        if (classH === '') continue;
        element.classList.add(classH)
    }
    for (let styleKey in style) {
        element.style[styleKey] = style[styleKey]
    }
    return element;
}

function createElement(tag = '', classList = '', styles = {}, attributes = {}) {
    const element = document.createElement(tag)
    for (let styleClass of classList.split(' ')) {
        if (styleClass === '') {
            continue
        }
        element.classList.add(styleClass)
    }
    for (let key in styles) {
        element.style[key] = styles[key]
    }
    for (let key in attributes) {
        if (key === 'text') {
            element.textContent = attributes[key]
            continue
        }
        element.setAttribute(key, attributes[key])
    }
    return element;
}

function appendElement(element = new Node(), ...child) {
    for (let childElement of child) {
        element.appendChild(childElement)
    }
    return element
}

function hideLeftNavbar() {
    const leftNavbar = document.querySelector('#leftNavbar')
    const toggleLeftNavbar = document.querySelector('#toggleLeftNavbar')
    const contentContainer = document.querySelector('#contentContainer')
    toggleLeftNavbar.classList.remove('rounded-left')
    toggleLeftNavbar.classList.add('rounded-right')
    toggleLeftNavbar.children.item(0).textContent = '>'
    toggleLeftNavbar.style.left = '0'
    toggleLeftNavbar.setAttribute('onclick', 'openLeftNavbar()')
    leftNavbar.style.transform = 'translateX(-100%)'
    contentContainer.style.transform = 'translateX(0)'
    setTimeout(() => {
        contentContainer.style.maxWidth = ""
        contentContainer.style.width = "100vw"
    }, 700)

}

function openLeftNavbar() {
    const leftNavbar = document.querySelector('#leftNavbar')
    const toggleLeftNavbar = document.querySelector('#toggleLeftNavbar')
    const contentContainer = document.querySelector('#contentContainer')
    toggleLeftNavbar.classList.remove('rounded-right')
    toggleLeftNavbar.classList.add('rounded-left')
    toggleLeftNavbar.children.item(0).textContent = '<'
    toggleLeftNavbar.setAttribute('onclick', 'hideLeftNavbar()')
    leftNavbar.style.transform = 'translateX(0)'
    toggleLeftNavbar.style.left = 'calc(20% - 20px)'
    contentContainer.style.transform = 'translateX(20vw)'
    setTimeout(() => {
        contentContainer.style.width = "80vw"
    }, 700)
}

for (let classStyle of 'text-dark'.split(' ')) {
    body.classList.add(classStyle)
}
body.style.backgroundColor = '#f1f1f1'
const navbar = createElement('nav', 'w-100 shadow-lg d-flex justify-content-between align-items-center pl-4', {
    height: '80px',
    position: 'fixed',
    zIndex: 3,
    top: 0,
    left: 0,
})
let navUl = appendElement(
    createElement('ul',
        'navbar nav justify-content-start align-items-center w-50',
        {}, {}),
    appendElement(
        createElement('li', 'nav-item'),
        createElement('img', 'navbar-brand', {}, {
            'src': '/assets/favicon.ico',
            'width': '50px',
            'alt': 'warehouse-logo.ico'
        })
    ))
let activeParentNavbarName = ''
for (let navbarItem of navbarItems) {
    const li = createElement('li', 'nav-item')
    if (location.pathname.startsWith(navbarItem.path)) {
        activeParentNavbarName = navbarItem.name
    }
    if (navbarItem.name === 'Dashboard' && notificationsCount > 0) {
        navUl.append(appendElement(li,
            appendElement(
                createElement('a', 'text-dark nav-link' + (activeParentNavbarName.includes(navbarItem.name) ? ' active' : ''), {}, {
                    'href': navbarItem.url,
                    'text': navbarItem.name
                }),
                createElement('span', 'badge badge-pill badge-danger', {}, {
                    text: notificationsCount
                })
            )
        ))
    } else {
        navUl.append(appendElement(li,
            createElement('a', 'text-dark nav-link' + (activeParentNavbarName.includes(navbarItem.name) ? ' active' : ''), {}, {
                'href': navbarItem.url,
                'text': navbarItem.name
            })))
    }

}
navbar.appendChild(navUl)
navbar.appendChild(appendElement(
    createElement('div', 'shadow mr-3 d-flex align-items-center justify-content-center',
        {
            width: '200px',
            height: "50px"
        }
    ),
    createElement("p", 'm-0', {}, {text: user.last_name + " " + user.first_name}),
    appendElement(
        createElement('a', '', {}, {href: '/auth/logout'}),
        createElement("img", "ml-3",
            {},
            {
                src: "/assets/icons/logout-icon.png",
                width: '20'
            }
        )
    )
))
body.appendChild(navbar)

if (activeParentNavbarName === '') {
    body.innerHTML = '<div class="d-flex align-items-center justify-content-center h-100 pb-5"> ' +
        '    <div class="text-center"> ' +
        '        <h1 style="font-size: 10rem">404</h1> ' +
        '        <p>This page not found. Go to <a href="/" class="nav-link">home page.</a></p> ' +
        '    </div> ' +
        '</div>'
}

const leftNavbar = createElement('div', 'h-100 shadow py-4',
    {
        'overflowY': 'hidden',
        'width': '20%',
        'maxWidth': "20%",
        transition: 'transform linear 0.5s',
        position: 'absolute',
        left: 0,
        top: '80px',
        bottom: 0,
        zIndex: 1,
        backgroundColor: '#f1f1f1'
    }, {
        id: 'leftNavbar'
    })
let find = false
for (let obj of leftNavbarItems[activeParentNavbarName]) {
    if (location.pathname === obj.url) {
        find = true
    }
    if (obj.name === "Notifications" &&
        location.pathname !== '/dashboard/notifications' &&
        notificationsCount !== null && notificationsCount > 0) {
        leftNavbar.appendChild(appendElement(
            createElement('div', 'px-5'),
            appendElement(
                createElement('a', 'nav-link text-dark link' + (location.pathname.includes(obj.url) ? ' active' : ''),
                    {},
                    {
                        href: obj.url,
                        text: obj.name
                    }),
                createElement('span', 'badge badge-danger', {}, {
                    text: notificationsCount
                })
            )
        ))
    } else {
        leftNavbar.appendChild(appendElement(
            createElement('div', 'px-5'),
            createElement('a', 'nav-link text-dark link' + (location.pathname.includes(obj.url) ? ' active' : ''),
                {},
                {
                    href: obj.url,
                    text: obj.name
                })
        ))
    }

}
const hideLeftNavbarBtn = appendElement(
    createElement('div', 'shadow rounded-left d-flex justify-content-center align-items-center',
        {
            position: 'absolute',
            left: 'calc(20% - 20px)',
            top: '42vh',
            width: '20px',
            height: '70px',
            cursor: 'pointer',
            zIndex: '2',
            transition: 'all linear 0.5s'
        },
        {
            'id': 'toggleLeftNavbar',
            'onclick': 'hideLeftNavbar()'
        }),
    createElement('span', 'font-weight-bold', {},
        {
            text: '<',
            fontSize: '1.5rem'
        }
    )
)
if (find) {
    main.appendChild(hideLeftNavbarBtn)
    main.appendChild(leftNavbar)
} else {
    leftNavbar.remove()
    hideLeftNavbarBtn.remove()
}

body.style.width = '100vw'
body.style.height = '100vh'
content.classList.add('container')
if (find) {
    body.style.overflow = 'hidden'
    content.style.overflowY = 'auto'
    content.style.width = '100%'
    content.style.height = 'calc(100vh - 100px)'
    main.appendChild(appendElement(
        createElement('div', 'h-100',
            {
                overflowY: 'hidden',
                width: find ? '80vw' : '100vw',
                maxWidth: find ? '80vw' : '100vw',
                height: 'calc(100vh - 100px)',
                transition: 'transform linear 0.5s, width ease-out 0.5s',
                position: 'absolute',
                top: '80px',
                transform: find ? 'translateX(20vw)' : 'translateX(0)',
                bottom: 0
            },
            {id: 'contentContainer'}),
        content
    ))
    body.appendChild(main)
} else {
    main.remove()
    body.appendChild(appendElement(
        createElement("div", '', {
            width: '100vw',
            height: 'calc(100vh - 80px)',
            overflowY: 'auto',
            position: 'fixed',
            top: '80px'
        }),
        content
    ))
}

const scripts = document.querySelectorAll('script')
for (let script of scripts) {
    body.appendChild(script)
}



